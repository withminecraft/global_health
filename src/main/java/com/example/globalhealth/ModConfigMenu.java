package com.example.globalhealth;

import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.common.ForgeConfigSpec;

public class ModConfigMenu {
    public static void register() {
        ModLoadingContext.get().registerExtensionPoint(
            ConfigScreenHandler.ConfigScreenFactory.class,
            () -> new ConfigScreenHandler.ConfigScreenFactory((client, parent) -> {
                // 创建简单的配置界面
                return new net.minecraft.client.gui.screens.Screen(parent.getTitle()) {
                    // 这里可以添加自定义配置界面
                    // 实际开发中可以使用Cloth Config API创建更美观的界面
                };
            })
        );
    }
}