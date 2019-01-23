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
import com.susu.studentcity.models.news.NewsModel;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ListNewsAdapter extends RecyclerView.Adapter<ListNewsAdapter.ViewHolder> {

    private ArrayList<NewsModel> news;
    private ItemClickListener callback;
    private Context context;

    public ListNewsAdapter(Context context,
                           ArrayList<NewsModel> news,
                           ItemClickListener itemClickListener) {
        this.context  = context;
        this.news     = news;
        this.callback = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(context == null) return null;

        View itemView = LayoutInflater.from(context).inflate(R.layout.item_of_news_list,
                parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        if(news == null) return;

        if(callback != null)
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onClick(news.get(position));
                }
            });

        NewsModel post = news.get(position);
        if(post.getText() != null) {
            holder.setTextContentView(post.getText());
        } else {
            holder.hideText();
        }

        String photo = post.getPhoto();
        if(photo != null) {
            holder.setImageContentView(photo);
        }
        else holder.hideImage();
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    public void update(ArrayList<NewsModel> news) {
        this.news = news;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textContentView;
        private ImageView imageContentView;

        private View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView    = itemView;
            textContentView  = itemView.findViewById(R.id.textContentView);
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

    public interface ItemClickListener {
        void onClick(NewsModel news);
    }
}