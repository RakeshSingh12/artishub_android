package com.artishub.app.helpers;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.EditText;
import android.widget.TextView;


public class FontManager {

    public enum FontType {
        FONT_DS_DIGIT_BOLD("fonts/ds_digitb.TTF"),
        FONT_HELVETICA_NUE_BOLD("fonts/helvetica_neu_bold.ttf"),
        FONT_HELVETICA_NUE__CONDENSED_BOLD("fonts/helveticaneue_condensedbold.otf");
//        FONT_DIN_PRO_COND_BOLD("fonts/DINPro-CondBold.otf"),
//        FONT_DIN_PRO_BOLD("fonts/DINPro-Bold.otf"),
//        FONT_DIN_PRO_MEDIUM("fonts/DINPro-Medium.otf"),
//        FONT_DIN_PRO__COND_MEDIUM("fonts/DINPro-CondMedium.otf"),
//        FONT_DIN_PRO_LIGHT("fonts/DINPro-Light.otf"),
//        FONT_DIN_PRO("fonts/DINPro.otf"),
//        FONT_DIN_PRO_COND("fonts/DINPro-Cond.otf"),
//        FONT_BADGE_ICON("fonts/Badge.ttf");

        private String type;

        FontType(String type) {
            this.type = type;

        }

        public static String fromType(FontType fontType) {
            if (fontType != null) {
                for (FontType typeEnum : FontType.values()) {
                    if (fontType == typeEnum) {
                        return typeEnum.type;
                    }
                }
            }
            return null;
        }

    }

    /*
    * set font on multiple textviews
    * */
    public static void setFontFace(final FontType fontType, final Context context, TextView... textViews) {
        // final FontType fontType = FontType.FONT_LATO_REGULAR;

        for (TextView tv : textViews)
            // tv.setTypeface(type);

            tv.setTypeface(Typeface.createFromAsset(context.getAssets(), FontType.fromType(fontType)));
    }
/*
* set font on multiple editTexts
* */

    public static void setFontFace(final FontType fontType, final Context context, EditText... editTexts) {

        //final FontType fontType = FontType.FONT_LATO_REGULAR;

        for (EditText edt : editTexts)
            // tv.setTypeface(type);

            edt.setTypeface(Typeface.createFromAsset(context.getAssets(), FontType.fromType(fontType)));
    }


    /**
     * Apply font face to each textview, button, edittext element in the activity view childeren
     * Note: Should be called after setcontentview method
     */
//    public static void applyFontRegular(final Context context, final View root) {
//        final FontType fontType = FontType.FONT_OPEN_SANS_REGULAR;
//        try {
//            if (root instanceof ViewGroup) {
//                ViewGroup viewGroup = (ViewGroup) root;
//                for (int i = 0; i < viewGroup.getChildCount(); i++)
//                    applyFontRegular(context, viewGroup.getChildAt(i));
//            } else if (root instanceof TextView)
//                ((TextView) root).setTypeface(Typeface.createFromAsset(context.getAssets(), FontType.fromType(fontType)));
//            else if (root instanceof EditText)
//                ((EditText) root).setTypeface(Typeface.createFromAsset(context.getAssets(), FontType.fromType(fontType)));
//            else if (root instanceof Button)
//                ((Button) root).setTypeface(Typeface.createFromAsset(context.getAssets(), FontType.fromType(fontType)));
//
//        } catch (Exception e) {
//            Log.e("Font Error", String.format("Error occured when trying to apply %s font for %s view", FontType.fromType(fontType), root));
//            e.printStackTrace();
//        }
//    }


}
