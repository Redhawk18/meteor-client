/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.misc;

import meteordevelopment.meteorclient.settings.IntSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;

import meteordevelopment.orbit.EventHandler;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;

public class AntiDesync extends Module {
    private int timer = 0;

    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final Setting<Integer> seconds = sgGeneral.add(new IntSetting.Builder()
        .name("seconds")
        .description("The amount of seconds until a new close inventory packet is sent.")
        .defaultValue(30)
        .sliderMin(5)
        .sliderMax(250)
        .build()
    );

    public AntiDesync() { super(Categories.Misc, "anti-desync", "Sends a close inventory packet to prevent desync."); }

    @EventHandler
    private void onTick() {
        if (timer++ != seconds.get() * 20) return;
        timer = 0;

        mc.getNetworkHandler().sendPacket(new CloseHandledScreenC2SPacket(0));
    }
}
