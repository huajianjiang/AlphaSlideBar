package com.github.huajianjiang.sample.alphaslidebar;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Author: Huajian Jiang
 * <br>Date: 2017/7/9
 * <br>Email: developer.huajianjiang@gmail.com
 */
public class Dummy {

    public static final String[] STRINGS= {
            "Subclasses of Layout use this constructor to set the display text, width, and other standard properties.",
            "CharSequence: the text to render",
            "TextPaint: the default paint for the layout. Styles can override various attributes of the paint.offset of the first character to be ellipsized away, relative to the start of the line",
            "float: factor by which to scale the font size to get the default line spacing",
            "Draw this Layout on the specified canvas, with the highlight path drawn between the background and the text.The return value is negative to match the Paint.ascent() convention.",
            "Return the base alignment of this layout.",
            "Returns the number of extra pixels of descent padding in the bottom line of the Layout.Return the vertical position-of the baseline of the specified line.",
            "Fills in the specified Path with a representation of a cursor at the specified offset. This will often be a vertical line but can be multiple discontinuous lines in text with multiple directionalities.",
            "Return how wide a layout must be in order to display the specified text with one line per paragraph",

    };

    public static List<Item> getItems(){
        List<Item> items=new ArrayList<>();
        for (int i = 0; i < 28; i++) {
            Item item=new Item();
            item.txt = STRINGS[i%STRINGS.length];
            items.add(item);
        }
        return items;
    }

}
