package dev.mayaqq.estrogen.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.mayaqq.estrogen.config.EstrogenConfig;
import dev.mayaqq.estrogen.resources.EstrogenSplashLoader;
import net.minecraft.client.resources.SplashManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(SplashManager.class)
public class SplashManagerMixin {
    @ModifyReturnValue(
            method = "prepare(Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)Ljava/util/List;",
            at = @At("RETURN")
    )
    private List<String> modifySplashList(List<String> list) {
        if (EstrogenConfig.client().estrogenSplashes.get()) {
            list.addAll(EstrogenSplashLoader.INSTANCE.splashes);
        }
        return list;
    }
}
