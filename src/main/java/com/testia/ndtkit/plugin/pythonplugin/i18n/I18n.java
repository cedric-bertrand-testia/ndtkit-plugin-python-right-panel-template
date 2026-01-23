package com.testia.ndtkit.plugin.pythonplugin.i18n;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import agi.ndtkit.api.common.localization.NILocalizedString;
import agi.ndtkit.api.common.localization.NILocalizedStringProvider;
import agi.ndtkit.api.common.localization.NISimpleLocalizedString;

public class I18n {

    /** The BUNDLE_NAME. */
    public static ResourceBundle bundle;

    private static I18n instance = null;

    private static NILocalizedStringProvider provider;

    /** Gets the localized string provider */
    public static NILocalizedStringProvider getProvider() {
        if (provider == null) {
            bundle = ResourceBundle.getBundle(I18n.class.getPackage().getName() + ".i18n", Locale.getDefault());
            provider = new NILocalizedStringProvider(bundle);
        }
        return provider;
    }

    /** Gets the instance. */
    public static I18n instance() {
        if (instance == null) {
            instance = new I18n();
        }
        return instance;
    }

    /**
     * Private constructor.
     */
    private I18n() {
    }

    /**
     * Get the localized string corresponding to key.
     * @param key The localization key to be retrieved
     * @return The localization value corresponding to the given key.
     */
    public NILocalizedString getLocalizedString(final String key) {
        try {
            return getProvider().getString(key);
        } catch (MissingResourceException e) {
            return new NISimpleLocalizedString('!' + key + '!');
        }
    }

    /**
     * @param key The localization key to be retrieved
     * @return The localization value corresponding to the given key.
     */
    public String getString(final String key) {
        try {
            return getProvider().getString(key).getValueForCurrentLocale();
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }

    /**
     * @param key The localization key to be retrieved.
     * @param args The arguments to be included into string.
     * @return The localization value corresponding to the given key.
     */

    public static String getString(final String key, final Object... args) {
        try {
            return getProvider().getString(key, args).getValueForCurrentLocale();
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }

    /**
     * Get the bundle.
     * @return The bundle.
     */
    public static ResourceBundle getBundle() {
        // to be sure that bundle is initialized.
        getProvider();
        return bundle;
    }
}
