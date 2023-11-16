package io.github.projecthsi.mobarena.dev;

import io.github.projecthsi.mobarena.plugin.MobArena;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.StyleBuilderApplicable;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.serializer.ansi.ANSIComponentSerializer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * This logging class is intended to be used in situations where normal logging is ineffective.
 * This logging is recursive, and shouldn't be used lightly.
 */
public class FullLog {
    // Banner Colors
    private static final TextColor indentureColor = NamedTextColor.RED;
    private static final TextColor staticTextColor = NamedTextColor.GREEN;
    private static final TextColor genericPackageTextColor = NamedTextColor.YELLOW;
    private static final TextColor genericClassTextColor = NamedTextColor.AQUA;

    // KV Colors
    private static final TextColor keyTextColor = NamedTextColor.RED;
    private static final TextColor packageTextColor = NamedTextColor.YELLOW;
    private static final TextColor classTextColor = NamedTextColor.AQUA;
    private static final TextColor valueTextColor = NamedTextColor.GREEN;

    private static TagResolver generateBannerIndenture(int indentureCount) {
        String indenture = "-".repeat(indentureCount * 2);

        TagResolver indentureColor = Placeholder.styling("indenture-color", (StyleBuilderApplicable) FullLog.indentureColor);
        TagResolver indenturePlaceholder = Placeholder.unparsed("indenture", indenture);

        return Placeholder.component("indenture", MiniMessage.miniMessage().deserialize("<indenture-color><indenture><indenture-color>", indentureColor, indenturePlaceholder));
    }

    private static TagResolver generateBannerPackage(Object object) {
        String packageName = object.getClass().getPackageName() + ".";

        TagResolver packageColor = Placeholder.styling("package-color", (StyleBuilderApplicable) genericPackageTextColor);
        TagResolver packagePlaceholder = Placeholder.unparsed("package", packageName);

        return Placeholder.component("package", MiniMessage.miniMessage().deserialize("<package-color><package><package-color>", packageColor, packagePlaceholder));
    }

    private static TagResolver generateBannerClass(Object object) {
        String className = object.getClass().getName();

        TagResolver classColor = Placeholder.styling("class-color", (StyleBuilderApplicable) genericClassTextColor);
        TagResolver classPlaceholder = Placeholder.unparsed("class", className);

        return Placeholder.component("class", MiniMessage.miniMessage().deserialize("<class-color><class><class-color>", classColor, classPlaceholder));
    }

    private static Component generateBanner(Object object, int recursion) {
        final String bannerPlaceHolder = "<indenture> <package><class> <static-color>Full Log</static-color> <indenture>";

        return MiniMessage.miniMessage().deserialize(bannerPlaceHolder,
                generateBannerIndenture(recursion),
                generateBannerPackage(object),
                generateBannerClass(object),
                Placeholder.styling("static-color", staticTextColor));
    }



    private static TagResolver generateKVIndenture(int indentureCount) {
        String indenture = "-".repeat(indentureCount * 2);

        TagResolver indentureColor = Placeholder.styling("indenture-color", (StyleBuilderApplicable) FullLog.indentureColor);
        TagResolver indenturePlaceholder = Placeholder.unparsed("indenture", indenture);

        return Placeholder.component("indenture", MiniMessage.miniMessage().deserialize("<indenture-color><indenture><indenture-color>", indentureColor, indenturePlaceholder));
    }

    private static TagResolver generateKVKey(String key) {
        TagResolver keyColor = Placeholder.styling("key-color", (StyleBuilderApplicable) keyTextColor);
        TagResolver keyPlaceholder = Placeholder.unparsed("key", key);

        return Placeholder.component("key", MiniMessage.miniMessage().deserialize("<indenture-color><indenture><indenture-color>", keyColor, keyPlaceholder));
    }

    private static TagResolver generateKVValue(String value) {
        TagResolver valueColor = Placeholder.styling("value-color", (StyleBuilderApplicable) valueTextColor);
        TagResolver valuePlaceholder = Placeholder.unparsed("value", value);

        return Placeholder.component("value", MiniMessage.miniMessage().deserialize("<indenture-color><indenture><indenture-color>", valueColor, valuePlaceholder));
    }

    private static TagResolver generateKVPackage(Object object) {
        String packageName = object.getClass().getPackageName() + ".";

        TagResolver packageColor = Placeholder.styling("package-color", (StyleBuilderApplicable) packageTextColor);
        TagResolver packagePlaceholder = Placeholder.unparsed("package", packageName);

        return Placeholder.component("package", MiniMessage.miniMessage().deserialize("<package-color><package><package-color>", packageColor, packagePlaceholder));
    }

    private static TagResolver generateKVClass(Object object) {
        String className = object.getClass().getName();

        TagResolver classColor = Placeholder.styling("class-color", (StyleBuilderApplicable) classTextColor);
        TagResolver classPlaceholder = Placeholder.unparsed("class", className);

        return Placeholder.component("class", MiniMessage.miniMessage().deserialize("<class-color><class><class-color>", classColor, classPlaceholder));
    }

    private static Component generateKV(String key, Object value, int recursion) {
        final String bannerPlaceHolder = "<indenture> <package><class>";

        return MiniMessage.miniMessage().deserialize(bannerPlaceHolder,
                generateKVIndenture(recursion),
                generateKVKey(key),
                generateKVValue(String.valueOf(value)),
                generateKVPackage(value),
                generateKVClass(value));
    }

    public static void doFullLog(Object object, int maxRecursion) {
        doFullLog(object, maxRecursion, 0);
    }

    private static void doFullLog(Object object, int maxRecursion, int currentRecursion) {
        currentRecursion++;

        if (maxRecursion == currentRecursion) return;

        Logger logger = MobArena.getInstance().getLogger();
        ANSIComponentSerializer ansiComponentSerializer = ANSIComponentSerializer.ansi();

        ArrayList<Object> objectsToBeAnalyzed = new ArrayList<>();

        logger.severe(ansiComponentSerializer.serialize(generateBanner(object, currentRecursion)));

        for (Field declaredField : object.getClass().getDeclaredFields()) {
            try {
                logger.severe(ansiComponentSerializer.serialize(generateKV(declaredField.getName(), declaredField.get(object), currentRecursion)));

                /* we do the analysis adding here, since if the declaredField is protected by the class,
                it'll have already failed as the above statement. */
                // we do testing to check if the object is strictly the same, so we don't end up with infinite recursion.
                if (!declaredField.getType().isPrimitive() && !(declaredField.get(object) == object)) {
                    objectsToBeAnalyzed.add(declaredField.get(object));
                }
            } catch (Exception e) {
                // no need to run any code here.
            }
        }

        for (Method declaredMethod : object.getClass().getDeclaredMethods()) {
            try {
                /* we need to do some checking otherwise we might be running code that breaks the class (setters, for
                instance.). but we also need to check if the method takes no arguments (we don't pass any special
                arguments.). */
                if (!declaredMethod.getName().startsWith("get")) continue;
                if (!(declaredMethod.getParameterCount() == 0)) continue;

                logger.severe(ansiComponentSerializer.serialize(generateKV(declaredMethod.getName(), declaredMethod.invoke(object), currentRecursion)));

                /* we do the analysis adding here, since if the declaredMethod is protected by the class,
                it'll have already failed as the above statement. */
                if (!declaredMethod.getReturnType().isPrimitive() && !(declaredMethod.get(object) == object)) {
                    objectsToBeAnalyzed.add(declaredMethod.invoke(object));
                }
            } catch (Exception e) {
                // no need to run any code here.
            }
        }

        for (Object newObject : objectsToBeAnalyzed) {
            doFullLog(newObject, maxRecursion, currentRecursion);
        }

        logger.severe(ansiComponentSerializer.serialize(generateBanner(object, currentRecursion)));
    }
}
