package com.example.globalhealth;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import java.util.List;
import java.util.UUID;

public class EntityEvents {
    private static final UUID HEALTH_MODIFIER_UUID = UUID.fromString("a5f2b0d9-0d4a-4c3d-95a2-4d4a7c5d3b1a");

    @SubscribeEvent
    public void onEntitySpawn(EntityJoinLevelEvent event) {
        if (!(event.getEntity() instanceof LivingEntity entity) || event.getLevel().isClientSide()) {
            return;
        }
        
        ResourceLocation entityId = EntityType.getKey(entity.getType());
        if (entityId == null) return;
        
        String entityName = entityId.toString();
        
        if (shouldModifyEntity(entityName)) {
            // 1. 记录原始血量和最大血量
            float originalHealth = entity.getHealth();
            float originalMaxHealth = entity.getMaxHealth();
            float healthRatio = originalHealth / originalMaxHealth; // 计算原始血量比例

            // 2. 应用血量倍率修饰符
            modifyEntityHealth(entity);
            
            // 3. 计算新的血量（按原始比例应用到新最大血量）
            float newMaxHealth = entity.getMaxHealth();
            float newHealth = newMaxHealth * healthRatio;
            
            // 4. 确保新血量在合法范围内
            newHealth = Math.min(newHealth, newMaxHealth);
            newHealth = Math.max(newHealth, 1.0f); // 至少保留1点血
            
            // 5. 设置新血量
            entity.setHealth(newHealth);
        }
    }

    private boolean shouldModifyEntity(String entityName) {
        List<? extends String> blacklist = Config.BLACKLIST.get();
        List<? extends String> whitelist = Config.WHITELIST.get();
        boolean useWhitelist = Config.USE_WHITELIST_MODE.get();

        if (useWhitelist) {
            return whitelist.contains(entityName);
        }
        return !blacklist.contains(entityName);
    }

    private void modifyEntityHealth(LivingEntity entity) {
        double multiplier = Config.HEALTH_MULTIPLIER.get();
        double baseHealth = entity.getAttributeBaseValue(Attributes.MAX_HEALTH);
        double healthIncrease = (baseHealth * multiplier) - baseHealth;
        
        entity.getAttribute(Attributes.MAX_HEALTH).removeModifier(HEALTH_MODIFIER_UUID);
        
        AttributeModifier healthModifier = new AttributeModifier(
            HEALTH_MODIFIER_UUID,
            "Global Health Modifier",
            healthIncrease,
            AttributeModifier.Operation.ADDITION
        );
        
        entity.getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(healthModifier);
    }
}