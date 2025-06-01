package com.example.globalhealth;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod(GlobalHealthMod.MODID)
public class GlobalHealthMod {
    public static final String MODID = "globalhealthmod";

    public GlobalHealthMod() {
        // 注册配置文件和事件处理器
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        MinecraftForge.EVENT_BUS.register(new EntityEvents());
        
        // 如果是客户端，注册配置界面
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> ModConfigMenu::register);
    }
}