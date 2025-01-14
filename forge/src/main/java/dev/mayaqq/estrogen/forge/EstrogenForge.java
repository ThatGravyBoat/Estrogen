package dev.mayaqq.estrogen.forge;

import com.mojang.serialization.Codec;
import com.simibubi.create.foundation.config.ConfigBase;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.client.registry.EstrogenRenderer;
import dev.mayaqq.estrogen.config.EstrogenConfig;
import dev.mayaqq.estrogen.forge.loot.AddSpecialThighHigh;
import dev.mayaqq.estrogen.registry.EstrogenEvents;
import dev.mayaqq.estrogen.resources.thighhighs.ThighHighStyleLoader;
import net.minecraft.server.players.PlayerList;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;

import static dev.mayaqq.estrogen.Estrogen.MOD_ID;

@Mod(MOD_ID)
public class EstrogenForge {

    private static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIERS =
        DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, MOD_ID);

    public EstrogenForge() {
        // Config
        EstrogenConfig.register(ModLoadingContext.get()::registerConfig);

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Init Estrogen main class
        Estrogen.init();

        LOOT_MODIFIERS.register("special_thigh_highs", () -> AddSpecialThighHigh.CODEC);
        LOOT_MODIFIERS.register(modEventBus);

        if(FMLEnvironment.dist == Dist.CLIENT) {
            EstrogenRenderer.register();
        }
        modEventBus.addListener(EstrogenForge::init);
        modEventBus.addListener(EstrogenForge::onEntityAttributeCreation);
    }

    public static void init(final FMLCommonSetupEvent event) {
        event.enqueueWork(Estrogen::postInit);
    }

    public static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        EstrogenEvents.onEntityAttributeCreation().forEach(event::put);
    }
}