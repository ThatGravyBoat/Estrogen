package dev.mayaqq.estrogen.resources;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.ArrayList;
import java.util.Map;

public class EstrogenSplashLoader extends SimpleJsonResourceReloadListener {

    public static final EstrogenSplashLoader INSTANCE = new EstrogenSplashLoader(new Gson(), "splashes");

    public ArrayList<String> splashes = new ArrayList<>();

    public EstrogenSplashLoader(Gson gson, String directory) {
        super(gson, directory);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> object, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        this.splashes.clear();
        object.forEach((key, value) -> {
            if (key.getNamespace().equals("estrogen") && key.getPath().equals("splashes")) {
                value.getAsJsonObject().getAsJsonArray("splashes").forEach(element -> {
                    this.splashes.add(element.getAsString());
                });
            }
        });
    }
}
