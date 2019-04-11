package com.example.gem.reviewandroidcode.lang;

import android.content.Context;

import com.example.gem.reviewandroidcode.utils.FileUtils;
import com.example.gem.reviewandroidcode.utils.StringUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Languages manager
 */

public class Languages {
  private static final String LANGUAGE_DEFAULT = "en";
  private static final String LANGUAGE_ZH_CN = "zh-cn";
  private static final String LANGUAGE_ZHCN = "zhcn";

  private static Map<String, JsonObject> mLanguageMap = new HashMap<>();

  public static String getDeviceLanguage() {
    return Locale.getDefault().getLanguage();
  }

  public static String getString(Context context, String key) {
    String languageCode = getDeviceLanguage();
    if (languageCode.equalsIgnoreCase(LANGUAGE_ZH_CN))
      languageCode = LANGUAGE_ZHCN;
    JsonObject dict = getLangDict(context, languageCode);
    if (dict.has(key)) {
      return dict.get(key).getAsString();
    } else {
      /*Neu thieu tu trong file json thi su dung ngon ngu mac dinh (English)*/
      dict = getLangDict(context, LANGUAGE_DEFAULT);
      return dict.get(key).getAsString();
    }
  }
  /**
   * Get localized language dictionary
   */
  private static JsonObject getLangDict(Context context, String langCode) {
    langCode = langCode.toLowerCase();
    if (!mLanguageMap.containsKey(langCode)) {
      String dict = FileUtils.readAssetFile(context, "languages/" + langCode + ".json");
      if (StringUtils.isEmpty(dict)) {
        return getLangDict(context, LANGUAGE_DEFAULT);
      } else {
        mLanguageMap.put(langCode, new Gson().fromJson(dict, JsonObject.class));
      }
    }

    return mLanguageMap.get(langCode);
  }

  /*[DuanVH] Start Add*/
  private static JsonObject getLangDict(String langCode) {
    langCode = langCode.toLowerCase();
    if (!mLanguageMap.containsKey(langCode)) {
      String dict = FileUtils.readFile("Languages/" + langCode + ".json");
      if (StringUtils.isEmpty(dict)) {
        return getLangDict(LANGUAGE_DEFAULT);
      } else {
        mLanguageMap.put(langCode, new Gson().fromJson(dict, JsonObject.class));
      }
    }
    return mLanguageMap.get(langCode);
  }
  /*[DuanVH] End Add*/
}
