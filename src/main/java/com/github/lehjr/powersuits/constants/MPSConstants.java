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

package com.github.lehjr.powersuits.constants;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

public class MPSConstants {
    public static final String MOD_ID = "powersuits";
    public static final String RESOURCE_PREFIX = MPSConstants.MOD_ID + ":";
    public static final String TEXTURE_PREFIX = RESOURCE_PREFIX + "textures/";
    public static final String RESOURCE_DOMAIN = MOD_ID.toLowerCase();

    // temporary locations until model spec system up and running
    public static final String SEBK_AMROR_PANTS = TEXTURE_PREFIX + "item/armor/sebkarmorpants.png";
    public static final String SEBK_AMROR = TEXTURE_PREFIX + "item/armor/sebkarmor.png";
    public static final String CITIZEN_JOE_ARMOR_PANTS = TEXTURE_PREFIX + "item/armor/joearmorpants.png";
    public static final String CITIZEN_JOE_ARMOR = TEXTURE_PREFIX + "item/armor/joearmor.png";

    public static final ResourceLocation POWER_FIST_TEXTURE = new ResourceLocation(TEXTURE_PREFIX + "models/powerfist.png");

    public static final TranslationTextComponent CRAFTING_TABLE_CONTAINER_NAME  = new TranslationTextComponent("container.crafting");



    /**
     * Config -------------------------------------------------------------------------------------
     */
    public static final String CONFIG_PREFIX = "config." + MOD_ID + ".";

    /** HUD ---------------------------------------------------------------------------------------- */
    public static final String CONFIG_PREFIX_HUD = CONFIG_PREFIX + "hud.";
    public static final String CONFIG_HUD_TOGGLE_MODULE_SPAM = CONFIG_PREFIX_HUD + "enableModuleSpam";
    public static final String CONFIG_HUD_DISPLAY_HUD = CONFIG_PREFIX_HUD + "DisplayHUD";
    public static final String CONFIG_HUD_KEYBIND_HUD_X = CONFIG_PREFIX_HUD + "Xposition";
    public static final String CONFIG_HUD_KEYBIND_HUD_Y = CONFIG_PREFIX_HUD + "Yposition";
    public static final String CONFIG_HUD_USE_GRAPHICAL_METERS = CONFIG_PREFIX_HUD + "useGraphicalMeters";
    public static final String CONFIG_HUD_USE_24_HOUR_CLOCK = CONFIG_PREFIX_HUD + "use24HrClock";

    /** Recipes ------------------------------------------------------------------------------------- */
    public static final String CONFIG_PREFIX_RECIPES = CONFIG_PREFIX + "recipes.";
    public static final String CONFIG_RECIPES_USE_VANILLA = CONFIG_PREFIX_RECIPES + "useVanilla";

    /** General ------------------------------------------------------------------------------------ */
    public static final String CONFIG_PREFIX_GENERAL = CONFIG_PREFIX + "general.";
    public static final String CONFIG_GENERAL_ALLOW_CONFLICTING_KEYBINDS = CONFIG_PREFIX_GENERAL + "allowConflictingKeybinds";
    public static final String CONFIG_GENERAL_MAX_FLYING_SPEED = CONFIG_PREFIX_GENERAL + "maxFlyingSpeed";
    public static final String CONFIG_GENERAL_BASE_MAX_HEAT_HELMET = CONFIG_PREFIX_GENERAL + "maxHeatBaseHelmet";
    public static final String CONFIG_GENERAL_BASE_MAX_HEAT_CHESTPLATE = CONFIG_PREFIX_GENERAL + "maxHeatBaseChestplate";
    public static final String CONFIG_GENERAL_BASE_MAX_HEAT_LEGGINGS = CONFIG_PREFIX_GENERAL +  "maxHeatBaseLeggings";
    public static final String CONFIG_GENERAL_BASE_MAX_HEAT_FEET = CONFIG_PREFIX_GENERAL +  "maxHeatBaseFeet";
    public static final String CONFIG_GENERAL_BASE_MAX_HEAT_POWERFIST = CONFIG_PREFIX_GENERAL +  "maxHeatBasePowerFist";
    public static final String CONFIG_GENERAL_VEIN_MINER_ORE_LIST = CONFIG_PREFIX_GENERAL +  "maxVeinMinerOreList";
    public static final String CONFIG_GENERAL_VEIN_MINER_BLOCK_LIST = CONFIG_PREFIX_GENERAL +  "maxVeinMinerBlockList";

    /** Cosmetics ---------------------------------------------------------------------------------- */
    public static final String CONFIG_PREFIX_COSMETIC = CONFIG_PREFIX + "cosmetic.";
    public static final String CONFIG_COSMETIC_USE_LEGACY_COSMETIC_SYSTEM = CONFIG_PREFIX_COSMETIC + "useLegacyCosmeticSystem";
    public static final String CONFIG_COSMETIC_ALLOW_HIGH_POLLY_ARMOR_MODELS = CONFIG_PREFIX_COSMETIC + "allowHighPollyArmorModuels";
    public static final String CONFIG_COSMETIC_ALLOW_POWER_FIST_CUSTOMIZATOIN = CONFIG_PREFIX_COSMETIC + "allowPowerFistCustomization";


    /**
     * Modules ------------------------------------------------------------------------------------
     */

    /** Armor ---------------------------------------------------------------------------------- */
    public static final String ARMOR_POINTS = "armorPoints";
    public static final String ARMOR_VALUE_PHYSICAL = "armorPhysical";
    public static final String ARMOR_VALUE_ENERGY = "armorEnergy";
    public static final String ARMOR_ENERGY_CONSUMPTION = "armorEnergyPerDamage";
    public static final String KNOCKBACK_RESISTANCE = "knockbackResistance";
    public static final String MODULE_FIELD_STRENGTH = "fieldStrength";

    /** Energy Generation ---------------------------------------------------------------------- */
    public static final String ENERGY_GENERATION = "energyPerBlock";
    public static final String MOVEMENT_RESISTANCE = "movementResistance";
    public static final String ENERGY_GENERATED = "energyGenerated";
    public static final String HEAT_GENERATION_DAY = "daytimeHeatGen";
    public static final String HEAT_GENERATION_NIGHT = "nightTimeHeatGen";
    public static final String ENERGY_GENERATION_DAY = "daytimeEnergyGen";
    public static final String ENERGY_GENERATION_NIGHT = "nightTimeEnergyGen";
    /** Environmental -------------------------------------------------------------------------- */
    // Auto Feeder
    public static final String EATING_EFFICIENCY = "autoFeederEfficiency";
    public static final String EFFICIENCY = "efficiency";
    public static final String AUTO_FEEDER_ENERGY = "autoFeederEnergy";
    // Cooling System
    public static final String COOLING_BONUS = "coolingBonus";
    public static final String COOLING_ENERGY = "coolingEnergy";
    public static final String COOLING_POWER = "coolingPower";
    // Fluid Tank
    public static final String FLUID_TANK_SIZE = "fluidTankSize";
    public static final String HEAT_ACTIVATION_PERCENT = "heatActivationPercent";//Heat Activation Percent";
    public static final String ACTIVATION_PERCENT = "activationPercent";//"Activation Percent"
    // Mob Repulsor
    public static final String MOB_REPULSOR_ENERGY = "mobRepulsorEnergy";
    // Water Electrolyzer
    public static final String WATER_ELECTROLYZER_ENERGY = "waterElectrolyzerEnergy";
    /** Mining Enhancement --------------------------------------------------------------------- */
    // Generic
    public static final String DIAMETER ="diameter";
//    public static final String HARVEST_SPEED = "harvSpeed";
    public static final String ENCHANTMENT_LEVEL ="enchLevel";
    // AOE
    public static final String AOE_MINING_RADIUS = "aoeMiningDiameter";
    public static final String AOE_ENERGY = "aoeEnergy";
    // Aqua Affinity
    public static final String AQUA_ENERGY = "aquaEnergy";
    public static final String AQUA_POWER = "aquaPower";
    public static final String AQUA_HARVEST_SPEED = "aquaHarvSpeed";
    // Fortune
    public static final String FORTUNE_ENCHANTMENT_LEVEL ="fortuneLevel";
    public static final String FORTUNE_ENERGY_CONSUMPTION = "fortuneEnCon";
    // Silk Touch
    public static final String SILK_TOUCH_ENERGY_CONSUMPTION = "silkTouchEnCon";
    // Vein Miner
    public static final String VEIN_MINER_ENERGY = "veinMinerEnergy";

    /** Movement ------------------------------------------------------------------------------- */
    // Generic
    public static final String THRUST = "thrust";
    public static final String MULTIPLIER = "multiplier";
    // Blink Drive
    public static final String BLINK_DRIVE_RANGE = "blinkDriveRange";
    public static final String BLINK_DRIVE_ENERGY = "blinkDriveEnergy";
    public static final String RANGE = "range";
    // Dimensional Rift
    public static final String DIM_RIFT_ENERGY = "dimRiftEnergy";
    public static final String HEAT_GENERATION = "heatGen";
    // Flight Control
    public static final String VERTICALITY = "vertically";
    public static final String FLIGHT_VERTICALITY = "yLookRatio";
    // JetBoots
    public static final String JETBOOTS_THRUST = "jetbootsThrust";
    public static final String JETBOOTS_ENERGY = "jetbootsEnergy";
    // Jetpack
    public static final String JETPACK_THRUST = "jetpackThrust";
    public static final String JETPACK_ENERGY = "jetpackEnergy";
    // Jump Assist
    public static final String JUMP_ENERGY = "jumpEnergy";
    public static final String JUMP_POWER = "jumpPower";
    // Shock Absorber
    public static final String SHOCK_ENERGY = "shockEnergy";
    public static final String SHOCK_POWER = "shockPower";
    // Sprint Assist
    public static final String SPRINT_ENERGY_CONSUMPTION = "sprintEnergyCon";
    public static final String SPRINT_SPEED_MULTIPLIER = "sprintSpeedMult";
    public static final String SPRINT_ASSIST = "sprintAssist";
    public static final String FOOD_COMPENSATION = "sprintExComp";
    public static final String COMPENSATION = "compensation";
    public static final String WALKING_ENERGY_CONSUMPTION = "walkingEnergyCon";
    public static final String WALKING_SPEED_MULTIPLIER = "walkingSpeedMult";
    public static final String WALKING_ASSISTANCE = "walkingAssist";
    // Swim Assist
    public static final String SWIM_BOOST_AMOUNT = "underwaterMovBoost";
    public static final String SWIM_ENERGY = "swimEnergy";

    /** Special -------------------------------------------------------------------------------- */
    public static final String MAGNET_ENERGY = "magnetEnergy";
    public static final String MAGNET_POWER = "magnetPower";
    /** Tool ----------------------------------------------------------------------------------- */
    // Generic
    public static final String OVERCLOCK = "overclock";
    public static final String RADIUS = "radius";
    // Axe
    public static final String AXE_ENERGY = "axeEnergy";
    public static final String AXE_HARVEST_SPEED = "axeHarvestSpeed";
    // Diamond Pick
    public static final String DIAMOND_PICK_ENERGY = "diamondPickEnergy";
    // Flint and Steel
    public static final String FLINT_AND_STEELE_ENERGY = "flintAndSteelEnergy";
    // Hoe
    public static final String HOE_ENERGY = "hoeEnergy";
    public static final String HOE_HARVEST_SPEED = "hoeHarvestSpeed";
    // Leaf Blower
    public static final String LEAF_BLOWER_ENERGY = "leafBlowerCapEnergy";
    // Lux Capacitor
    public static final String LUX_CAP_ENERGY = "luxCapEnergy";
    public static final String RED = "red";
    public static final String GREEN = "green";
    public static final String BLUE = "blue";
    public static final String ALPHA = "alpha";
    public static final String RED_HUE = "redHue";
    public static final String BLUE_HUE = "blueHue";
    public static final String GREEN_HUE = "greenHue";
    public static final String OPACITY = "opacity";
    // Pickaxe
    public static final String PICKAXE_ENERGY = "pickaxeEnergy";
    public static final String PICKAXE_HARVEST_SPEED = "pickaxeHarvestSpeed";
    // Shears
    public static final String SHEARS_ENERGY = "shearsEnergy";
    public static final String SHEARS_HARVEST_SPEED = "shearsHarvestSpeed";
    // Shovel
    public static final String SHOVEL_ENERGY = "shovelEnergy";
    public static final String SHOVEL_HARVEST_SPEED = "shovelHarvestSpeed";

    /** Vision --------------------------------------------------------------------------------- */
    public static final String FOV = "fieldOfView";
    public static final String FIELD_OF_VIEW = "fOVMult";

    /** Weapon --------------------------------------------------------------------------------- */
    // Blade Launcher
    public static final String BLADE_DAMAGE = "spinBladeDam";
    public static final String BLADE_ENERGY = "spinBladeEnergyCon";
    // Lightning Summoner
    public static final String HEAT_EMISSION = "heatEmission";
    public static final String LIGHTNING_ENERGY = "lightningEnergyCon";

    // Melee Assist
    public static final String PUNCH_ENERGY = "punchEnergyCon";
    public static final String PUNCH_DAMAGE = "meleeDamage";
    public static final String PUNCH_KNOCKBACK = "meleeKnockback";
    public static final String IMPACT = "impact";
    public static final String CARRY_THROUGH = "carryThrough";
    // Plasma Cannon
    public static final String PLASMA_CANNON_ENERGY_PER_TICK = "plasmaEnergyPerTick";
    public static final String PLASMA_CANNON_DAMAGE_AT_FULL_CHARGE = "plasmaDamage";
    public static final String PLASMA_CANNON_EXPLOSIVENESS = "plasmaExplosiveness";
    public static final String AMPERAGE = "amperage";
    public static final String CREEPER = "creeper";
    public static final String VOLTAGE = "voltage";
    // Railgun Module
    public static final String RAILGUN_TOTAL_IMPULSE = "railgunTotalImpulse";
    public static final String RAILGUN_ENERGY_COST = "railgunEnergyCost";
    public static final String RAILGUN_HEAT_EMISSION = "railgunHeatEm";
    public static final String TIMER = "cooldown";
}
