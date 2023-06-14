package com.xiaoace.darkroom.utils;

import snw.jkook.event.Listener;

import static com.xiaoace.darkroom.Main.getInstance;

public class Tools {

    public static void addListener(Listener listener) {
        getInstance().getCore().getEventManager().registerHandlers(getInstance(), listener);
    }

}
