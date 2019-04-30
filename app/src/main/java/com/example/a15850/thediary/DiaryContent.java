package com.example.a15850.thediary;

import android.content.Context;

import com.example.a15850.thediary.database.Diary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DiaryContent {

    /**
     * An array of diary items.
     */
    public static final List<DiaryItem> ITEMS = new ArrayList<DiaryItem>();

    /**
     * A map of sample diary items, by ID.
     */
//    public static final Map<String, DiaryItem> ITEM_MAP = new HashMap<String, DiaryItem>();
//
//    private static final int COUNT = 25;

//    static {
//        // Add some sample items.
//        for (int i = 1; i <= COUNT; i++) {
//            addItem(createDiaryItem(i));
//        }
//    }

//    private static void addItem(DiaryItem item) {
//        ITEMS.add(item);
//        ITEM_MAP.put(item.id, item);
//    }
//
//    private static DiaryItem createDiaryItem(int position) {
//        return new DiaryItem(String.valueOf(position), "Item " + position, makeDetails(position),null);
//    }

    //
//    private static String makeDetails(int position) {
//        StringBuilder builder = new StringBuilder();
//        builder.append("Details about Item: ").append(position);
//        for (int i = 0; i < position; i++) {
//            builder.append("\nMore details information here.");
//        }
//        return builder.toString();
//    }

    /**
     * A diary item representing a piece of content.
     */
    public static class DiaryItem {
        public final String id;
        public final String title;
        public final String content;
        public final String real_diary_id;

        public DiaryItem(String id,String title, String content, String real_diary_id) {
            this.id = id;
            this.title = title;
            this.content = content;
            this.real_diary_id=real_diary_id;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
