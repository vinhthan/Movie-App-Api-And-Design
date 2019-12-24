package com.example.movieappapianddesign.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.movieappapianddesign.R;
import com.example.movieappapianddesign.model.Slide;
import java.util.List;

public class SlidePageAdapter extends PagerAdapter {
/*    private List<PopularMovies.Results> listSlide;
    private Context context;

    public SlidePageAdapter(List<PopularMovies.Results> listSlide, Context context) {
        this.listSlide = listSlide;
        this.context = context;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View slideLayout = inflater.inflate(R.layout.slide_item, container, false);

        //
        PopularMovies.Results slideMovie = listSlide.get(position);

        ImageView imgSlide = slideLayout.findViewById(R.id.imgSlide);
        Glide.with(context).load(slideMovie.getBackdropPath()).into(imgSlide);

*//*        ImageView slideImg = slideLayout.findViewById(R.id.imgSlide);
        TextView slideText = slideLayout.findViewById(R.id.txvSlideTitle);
        slideImg.setImageResource(listSlide.get(position).getImage());
        slideText.setText(listSlide.get(position).getTitle());*//*


        container.addView(slideLayout);
        return slideLayout;
    }



    @Override
    public int getCount() {
        return listSlide.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }*/



    private Context mContext;
    private List<Slide> mList;

    public SlidePageAdapter(Context mContext, List<Slide> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View slideLayout = inflater.inflate(R.layout.slide_item, null);

        ImageView slideImg = slideLayout.findViewById(R.id.imgSlide);
        TextView slideText = slideLayout.findViewById(R.id.txvSlideTitle);
        slideImg.setImageResource(mList.get(position).getImage());
        slideText.setText(mList.get(position).getTitle());

        container.addView(slideLayout);
        return slideLayout;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
