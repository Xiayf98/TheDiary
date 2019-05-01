package com.example.a15850.thediary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public static final List<Boolean> CHECKS=new ArrayList<>();//记录勾选状态

    /**
     * A map of sample diary items, by ID.
     */
    public static final Map<String, DiaryItem> ITEM_MAP = new HashMap<String, DiaryItem>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDiaryItem(i));
            CHECKS.add(false);
        }
    }

    private static void addItem(DiaryItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static DiaryItem createDiaryItem(int position) {
        return new DiaryItem(String.valueOf(position), "Item " + position, makeDetails(position),false);
    }

    //
    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A diary item representing a piece of content.
     */
    public static class DiaryItem {
        public final String id;
        public final String content;
        public final String details;
        public boolean edit;


        public DiaryItem(String id, String content, String details,boolean edit) {
            this.id = id;
            this.content = content;
            this.details = details;
            this.edit=edit;
        }
        public void setEdit(boolean edit){
            this.edit=edit;
        }
        @Override
        public String toString() {
            return content;
        }
    }
}
