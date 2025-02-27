/*
 * Copyright (c) 2021. MachineMuse, Lehjr
 *  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *      Redistributions of source code must retain the above copyright notice, this
 *      list of conditions and the following disclaimer.
 *
 *     Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.github.lehjr.powersuits.basemod;

import com.github.lehjr.numina.basemod.MuseLogger;
import com.github.lehjr.numina.config.ConfigHelper;
import com.github.lehjr.numina.util.capabilities.render.ModelSpecNBTCapability;
import com.github.lehjr.powersuits.constants.MPSConstants;
import com.google.common.collect.HashBiMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

// TODO: move to external mod, something like the recipe creator
/**
 * Saves and loads cosmetic presets to/from NBT files.
 */
public class CosmeticPresetSaveLoad {
    static final String EXTENSION = "dat";
    static String configDirString = "";

    public static void setConfigDirString(String configDirStringIn) {
        configDirString = configDirStringIn;
    }

    static String getConfigDirString() {
        if (configDirString.isEmpty()) { // fixme?
            configDirString = ConfigHelper.getConfigFolder(MPSConstants.MOD_ID).getAbsolutePath();
        }
        return configDirString;
    }

    public static Map<String, CompoundNBT> loadPresetsForItem(@Nonnull ItemStack itemStack) {
        return loadPresetsForItem(itemStack.getItem(), 0);
    }

    public static Map<String, CompoundNBT> loadPresetsForItem(Item item, int count) {

        Map<String, CompoundNBT> retmap = new HashMap<>();
        if (item == null || count > 4) {
            return HashBiMap.create(retmap);
        }

        // sub folder based on the item id
        String subfolder = item.getRegistryName().getPath();

        // path with subfolder
        Path directory = Paths.get(getConfigDirString(), "cosmeticpresets", subfolder);
        if (Files.exists(directory))
            try {
                Files.walkFileTree(directory, EnumSet.noneOf(FileVisitOption.class), 1, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(Path selectedPath, BasicFileAttributes attrs) throws IOException {
                        if (selectedPath.getFileName().toString().endsWith("." + EXTENSION)) {
                            String name = selectedPath.getFileName().toString().replaceFirst("[.][^.]+$", "");
                            CompoundNBT nbt = CompressedStreamTools.readCompressed(Files.newInputStream(selectedPath));

                            if (nbt != null && name != null && !name.isEmpty()) {
                            if (retmap.containsKey("id"))
                                System.out.println("MAP ALREADY HAS KEY");
                            if (retmap.containsValue(nbt))
                                System.out.println("MAP ALREADY HAS VALUE");
                                retmap.put(name, nbt);
                            }
                        }
                        return FileVisitResult.CONTINUE;
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        if (retmap.isEmpty()) {
            copyPresetsFromJar(getConfigDirString());
            return loadPresetsForItem(item, count + 1);
        }

        return HashBiMap.create(retmap);
    }

    public static void copyPresetsFromJar(String configDir) {
        Path sourcePath;
        FileSystem fileSystem = null;

        try {
            URI uri = CosmeticPresetSaveLoad.class.getResource("/data/powersuits/cosmeticpresets/").toURI();
            if ("jar".equals(uri.getScheme())) {
                // We're running within a jar file
                if (fileSystem == null) {
                    try {
                        // Create a reference to the filesystem
                        fileSystem = FileSystems.newFileSystem(uri, Collections.emptyMap());
                    } catch (FileSystemAlreadyExistsException lEx) {
                        // Sometimes newFileSystem seems to raise FileSystemAlreadyExistsException - this code gets around this problem.
                        fileSystem = FileSystems.getFileSystem(uri);
                    }
                }
                // Get hold of the path to the top level directory of the JAR file
                sourcePath = fileSystem.getPath("/");
                sourcePath = sourcePath.resolve("data/powersuits/cosmeticpresets");

            } else
                sourcePath = Paths.get(uri);

            Files.walkFileTree(sourcePath, EnumSet.noneOf(FileVisitOption.class), 10, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path selectedPath, BasicFileAttributes attrs) throws IOException {
                    if (selectedPath.getFileName().toString().endsWith("." + EXTENSION)) {
                        Path subFolder = selectedPath.getParent().getFileName();

                        // path with subfolder
                        Path target = Paths.get(configDir,
                                "cosmeticpresets", subFolder.toString(), selectedPath.getFileName().toString());
                        try {
                            // create dir
                            if (!Files.exists(target.getParent()))
                                Files.createDirectories(target.getParent());

                            // create file without overwriting
                            if (!Files.exists(target))
                                Files.copy(selectedPath, target);//, StandardCopyOption.REPLACE_EXISTING);
                        } catch(Exception e) {
                            // FIXME
                            MuseLogger.logException("Exception here: ", e);
                        }
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (Exception e) {
            MuseLogger.logException("Something happened here: ", e);
        }
    }

    /**
     * "adapted" from 1.7.10
     */
    public static byte[] compressGZip(CompoundNBT nbt) {
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        try {
            DataOutputStream dataoutputstream = new DataOutputStream(new GZIPOutputStream(bytearrayoutputstream));
            CompressedStreamTools.write(nbt, dataoutputstream);

            // bytearrayoutputstream only updates if dataoutputstream closes
            dataoutputstream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
        return bytearrayoutputstream.toByteArray();
    }

    /**
     * Save the model settings as a Json in the config folder using the itemstack id as the folder id
     */
    public static boolean savePreset(String presetName, @Nonnull ItemStack itemStack) {
        if (itemStack.isEmpty())
            return false;

        return itemStack.getCapability(ModelSpecNBTCapability.RENDER).map(spec->{
            // get the render tag for the item
            CompoundNBT renderTag =spec.getRenderTag().copy();
            if (renderTag != null) {
                return savePreset(itemStack.getItem().getRegistryName(), presetName, renderTag);
            }

        return false;
        }).orElse(false);
    }

    /**
     * This is not logical side specific.
     */
    public static boolean savePreset(ResourceLocation registryNameIn, String nameIn, CompoundNBT cosmeticSettingsIn) {
        // byte array
        byte [] byteArray = compressGZip(cosmeticSettingsIn);

        try {
            // sub folder based on the item id
            String subfolder = registryNameIn.getPath();

            // path with subfolder
            Path directory = Paths.get(getConfigDirString(), "cosmeticpresets", subfolder);

            try {
                Files.createDirectories(directory);
            } catch(Exception e) {
                // FIXME
                MuseLogger.logException("Exception here: ", e); // debugging during development
            }

            // final complete path
            Path fullPath = Paths.get(directory.toString(), nameIn + "." + EXTENSION);
            Files.write(fullPath, byteArray);
        } catch(Exception e) {
            MuseLogger.logException("Failed to saveButton preset: ", e);
            return false;
        }
        return true;
    }
}