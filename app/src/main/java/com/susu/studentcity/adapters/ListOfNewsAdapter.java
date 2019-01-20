package com.susu.studentcity.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.susu.studentcity.R;
import com.susu.studentcity.models.ImageLoader.ImageLoader;
import com.vk.sdk.api.model.VKApiPhoto;
import com.vk.sdk.api.model.VKApiPost;
import com.vk.sdk.api.model.VKAttachments;
import com.vk.sdk.api.model.VKList;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ListOfNewsAdapter extends RecyclerView.Adapter<ListOfNewsAdapter.ViewHolder> {

    private VKList<VKApiPost> walls;
    private ItemClickCallback callback;
    private Context context;

    public ListOfNewsAdapter(Context context, VKList<VKApiPost> walls, ItemClickCallback callback) {

        this.walls = new VKList<>();
        for (VKApiPost post :
                walls) {
            if (post.text == null || getPhoto(post) == null) continue;

            this.walls.add(post);
        }

        this.callback = callback;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_of_news_list,
                parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(walls == null) return;

        VKApiPost post = walls.get(position);
        if(post.text != null) {
            holder.setTextContentView(post.text);
        } else {
            holder.hideText();
        }

        String photo = getPhoto(post);
        if(photo != null) {
            holder.setImageContentView(photo);
        }
        else holder.hideImage();
    }

    private String getPhoto(VKApiPost post) {
        if(post.attachments.getCount() > 0) {
            for (VKAttachments.VKApiAttachment attachment :
                    post.attachments) {

                if(attachment.getType().equals("photo")) {
                    VKApiPhoto photo = (VKApiPhoto) attachment;

                    if(photo != null) {
                        if(photo.photo_1280 != null)
                            return photo.photo_1280;
                        else if(photo.photo_604 != null)
                            return photo.photo_604;
                        else return null;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return walls.getCount();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textContentView;
        private ImageView imageContentView;

        public ViewHolder(View itemView) {
            super(itemView);
            textContentView = itemView.findViewById(R.id.textContentView);
            imageContentView = itemView.findViewById(R.id.imageContentView);
        }

        private final String[] CHARS_OF_LINKS_PATTERN = {
                "[",
                "]",
                "|"
        };

        public void setTextContentView(String textContent) {
            String newContent = deleteLinkOfClub(textContent);
            newContent = deleteLinkOfClub(deleteLinkOfProfile(newContent));
            textContentView.setText(newContent);
            textContentView.setMovementMethod(LinkMovementMethod.getInstance());
        }

        private void setImageContentView(String imageContent) {
            ImageLoader imageLoader = new ImageLoader(imageContentView, imageContent);
            imageLoader.load();
        }

        private String deleteText(String text, String sourcePattern, String...chars) {
            try {
                String result = "";
                String[] texts = text.split("\n");
                for (int i = 0; i < texts.length; i++) {
                    do {
                        Pattern pattern = Pattern.compile(sourcePattern);
                        Matcher matcher = pattern.matcher(texts[i]);

                        if (matcher.matches() && matcher.groupCount() >= 1) {
                            String group = matcher.group(1);
                            texts[i] = texts[i].replace(group, "");

                            for (String inputChar :
                                    chars) {
                                texts[i] = texts[i].replace(inputChar, "");
                            }
                        } else break;
                    }while (true);
                }

                for (String str :
                        texts) {
                    result += String.format("%s\n", str);

                }

                return  result;
            }
            catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        private String deleteLinkOfClub(String textContent) {
            String PATTERN_FOR_LINK_OF_CLUB = ".+(club[0-9]+).+";
            return deleteText(textContent, PATTERN_FOR_LINK_OF_CLUB, CHARS_OF_LINKS_PATTERN);
        }

        private String deleteLinkOfProfile(String textContent) {
            String PATTERN_FOR_LINK_OF_PROFILE = ".+(id[0-9]+).+";
            return deleteText(textContent, PATTERN_FOR_LINK_OF_PROFILE, CHARS_OF_LINKS_PATTERN);
        }

        private void hideImage() {
            imageContentView.setVisibility(View.GONE);
        }

        private void hideText() {
            textContentView.setVisibility(View.GONE);
        }
    }

    public interface ItemClickCallback {
        void onClick(VKApiPost wall);
    }
}