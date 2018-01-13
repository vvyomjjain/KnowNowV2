package com.knownow.vyomjain.knownowv2;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

import javax.xml.transform.Result;
import javax.xml.transform.Source;

import static android.os.Build.VERSION_CODES.N;


/**
 * A simple {@link Fragment} subclass.
 */
public class SourceFragment extends Fragment {

    RecyclerView national;
    RecyclerView international;
    RecyclerView business;
    RecyclerView technology;
    RecyclerView sports;
    RecyclerView gaming;
    RecyclerView science;
    RecyclerView entertainment;

    ArrayList nationalList;
    ArrayList interlist;
    ArrayList busilist;
    ArrayList techlist;
    ArrayList sportlist;
    ArrayList gamelist;
    ArrayList sclist;
    ArrayList enterlist;

    public static SourceFragment newInstance() {
        SourceFragment fragment = new SourceFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_source, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        national = (RecyclerView)getActivity().findViewById(R.id.national);
        international = (RecyclerView)getActivity().findViewById(R.id.international);
        business = (RecyclerView)getActivity().findViewById(R.id.business);
        technology = (RecyclerView)getActivity().findViewById(R.id.tech);
        sports = (RecyclerView)getActivity().findViewById(R.id.sports);
        gaming = (RecyclerView)getActivity().findViewById(R.id.gaming);
        science = (RecyclerView)getActivity().findViewById(R.id.science);
        entertainment = (RecyclerView)getActivity().findViewById(R.id.entertainment);

        nationalList = new ArrayList<NewsSource>();
        interlist = new ArrayList<NewsSource>();
        busilist = new ArrayList<NewsSource>();
        techlist = new ArrayList<NewsSource>();
        gamelist = new ArrayList<NewsSource>();
        sportlist = new ArrayList<NewsSource>();
        sclist = new ArrayList<NewsSource>();
        enterlist = new ArrayList<NewsSource>();

        sportlist.add(new NewsSource("BBC Sport", R.drawable.bbc_sport_2017, "https://newsapi.org/v1/articles?source=bbc-sport&sortBy=top&apiKey=5cc8a7d553b24613af922ce6eda1b7d6"));
        sportlist.add(new NewsSource("ESPN CricInfo", R.drawable.espncrc, "https://newsapi.org/v1/articles?source=espn-cric-info&sortBy=latest&apiKey=5cc8a7d553b24613af922ce6eda1b7d6"));
        sportlist.add(new NewsSource("ESPN", R.drawable.espn2, "https://newsapi.org/v1/articles?source=espn&sortBy=top&apiKey=5cc8a7d553b24613af922ce6eda1b7d6"));
        sportlist.add(new NewsSource("FourFourTwo", R.drawable.fft, "https://newsapi.org/v1/articles?source=four-four-two&sortBy=latest&apiKey=5cc8a7d553b24613af922ce6eda1b7d6"));
        sportlist.add(new NewsSource("NFL News", R.drawable.nfl, "https://newsapi.org/v1/articles?source=nfl-news&sortBy=latest&apiKey=5cc8a7d553b24613af922ce6eda1b7d6"));

        busilist.add(new NewsSource("Business Insider", R.drawable.bi, "https://newsapi.org/v1/articles?source=business-insider&sortBy=latest&apiKey=5cc8a7d553b24613af922ce6eda1b7d6"));
        busilist.add(new NewsSource("Fortune", R.drawable.fortune2, "https://newsapi.org/v1/articles?source=fortune&sortBy=latest&apiKey=5cc8a7d553b24613af922ce6eda1b7d6"));
        busilist.add(new NewsSource("Wall Street Journal", R.drawable.wsj, "https://newsapi.org/v1/articles?source=the-wall-street-journal&sortBy=top&apiKey=5cc8a7d553b24613af922ce6eda1b7d6"));
        busilist.add(new NewsSource("CNBC", R.drawable.cnbc3, "https://newsapi.org/v1/articles?source=cnbc&sortBy=top&apiKey=5cc8a7d553b24613af922ce6eda1b7d6"));

        nationalList.add(new NewsSource("The Times of India", R.drawable.toi, "https://newsapi.org/v1/articles?source=the-times-of-india&sortBy=latest&apiKey=5cc8a7d553b24613af922ce6eda1b7d6"));
        nationalList.add(new NewsSource("The Hindu", R.drawable.th, "https://newsapi.org/v1/articles?source=the-hindu&sortBy=latest&apiKey=5cc8a7d553b24613af922ce6eda1b7d6"));

        interlist.add(new NewsSource("BBC", R.drawable.bbc2, "https://newsapi.org/v1/articles?source=bbc-news&sortBy=top&apiKey=5cc8a7d553b24613af922ce6eda1b7d6"));
        interlist.add(new NewsSource("The New York Times", R.drawable.tnyt, "https://newsapi.org/v1/articles?source=the-new-york-times&sortBy=top&apiKey=5cc8a7d553b24613af922ce6eda1b7d6"));
        interlist.add(new NewsSource("ABC News", R.drawable.abc, "https://newsapi.org/v1/articles?source=abc-news-au&sortBy=top&apiKey=5cc8a7d553b24613af922ce6eda1b7d6"));
        interlist.add(new NewsSource("Al Jazeera", R.drawable.aljzeera, "https://newsapi.org/v1/articles?source=al-jazeera-english&sortBy=latest&apiKey=5cc8a7d553b24613af922ce6eda1b7d6"));
        interlist.add(new NewsSource("The Telegraph", R.drawable.telegraph, "https://newsapi.org/v1/articles?source=the-telegraph&sortBy=latest&apiKey=5cc8a7d553b24613af922ce6eda1b7d6"));

        enterlist.add(new NewsSource("Entertainment Weekly", R.drawable.ew, "https://newsapi.org/v1/articles?source=entertainment-weekly&sortBy=top&apiKey=5cc8a7d553b24613af922ce6eda1b7d6"));
        enterlist.add(new NewsSource("Mashable", R.drawable.mash, "https://newsapi.org/v1/articles?source=mashable&sortBy=latest&apiKey=5cc8a7d553b24613af922ce6eda1b7d6"));
        enterlist.add(new NewsSource("MTV news", R.drawable.mtvn, "https://newsapi.org/v1/articles?source=mtv-news&sortBy=latest&apiKey=5cc8a7d553b24613af922ce6eda1b7d6"));
        enterlist.add(new NewsSource("BuzzFeed", R.drawable.buzz, "https://newsapi.org/v1/articles?source=buzzfeed&sortBy=latest&apiKey=5cc8a7d553b24613af922ce6eda1b7d6"));

        techlist.add(new NewsSource("Engadget", R.drawable.engaget, "https://newsapi.org/v1/articles?source=engadget&sortBy=latest&apiKey=5cc8a7d553b24613af922ce6eda1b7d6"));
        techlist.add(new NewsSource("Recode", R.drawable.recode, "https://newsapi.org/v1/articles?source=recode&sortBy=top&apiKey=5cc8a7d553b24613af922ce6eda1b7d6"));
        techlist.add(new NewsSource("The Verge", R.drawable.verge, "https://newsapi.org/v1/articles?source=the-verge&sortBy=latest&apiKey=5cc8a7d553b24613af922ce6eda1b7d6"));
        techlist.add(new NewsSource("tech chrunch", R.drawable.tc, "https://newsapi.org/v1/articles?source=techcrunch&sortBy=latest&apiKey=5cc8a7d553b24613af922ce6eda1b7d6"));
        techlist.add(new NewsSource("The Next Web", R.drawable.tnw2, "https://newsapi.org/v1/articles?source=the-next-web&sortBy=latest&apiKey=5cc8a7d553b24613af922ce6eda1b7d6"));

        sclist.add(new NewsSource("National Geographic", R.drawable.natgeo, "https://newsapi.org/v1/articles?source=national-geographic&sortBy=top&apiKey=5cc8a7d553b24613af922ce6eda1b7d6"));
        sclist.add(new NewsSource("New Scientist", R.drawable.newsc, "https://newsapi.org/v1/articles?source=new-scientist&sortBy=top&apiKey=5cc8a7d553b24613af922ce6eda1b7d6"));

        gamelist.add(new NewsSource("IGN", R.drawable.ign, "https://newsapi.org/v1/articles?source=ign&sortBy=latest&apiKey=5cc8a7d553b24613af922ce6eda1b7d6"));
        gamelist.add(new NewsSource("Polygon", R.drawable.polygon, "https://newsapi.org/v1/articles?source=polygon&sortBy=top&apiKey=5cc8a7d553b24613af922ce6eda1b7d6"));

        national.setHasFixedSize(true);
        international.setHasFixedSize(true);
        business.setHasFixedSize(true);
        technology.setHasFixedSize(true);
        sports.setHasFixedSize(true);
        gaming.setHasFixedSize(true);
        science.setHasFixedSize(true);
        entertainment.setHasFixedSize(true);

        national.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        international.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        business.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        technology.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        sports.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        gaming.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        science.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        entertainment.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        national.setAdapter(new SourceAdapter(getContext(), nationalList));
        international.setAdapter(new SourceAdapter(getContext(), interlist));
        business.setAdapter(new SourceAdapter(getContext(), busilist));
        technology.setAdapter(new SourceAdapter(getContext(), techlist));
        sports.setAdapter(new SourceAdapter(getContext(), sportlist));
        gaming.setAdapter(new SourceAdapter(getContext(), gamelist));
        science.setAdapter(new SourceAdapter(getContext(), sclist));
        entertainment.setAdapter(new SourceAdapter(getContext(), enterlist));

        national.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener(){
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent sourceNewsIntent = new Intent(getActivity(), SourceNewsActivity.class);
                        NewsSource source = (NewsSource) nationalList.get(position);
                        sourceNewsIntent.putExtra("url", source.getUrl());
                        sourceNewsIntent.putExtra("sourceName", source.getSource_name());
                        startActivity(sourceNewsIntent);
                    }
                }));

        international.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener(){
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent sourceNewsIntent = new Intent(getActivity(), SourceNewsActivity.class);
                        NewsSource source = (NewsSource) interlist.get(position);
                        sourceNewsIntent.putExtra("url", source.getUrl());
                        sourceNewsIntent.putExtra("sourceName", source.getSource_name());
                        startActivity(sourceNewsIntent);
                    }
                }));

        business.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener(){
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent sourceNewsIntent = new Intent(getActivity(), SourceNewsActivity.class);
                        NewsSource source = (NewsSource) busilist.get(position);
                        sourceNewsIntent.putExtra("url", source.getUrl());
                        sourceNewsIntent.putExtra("sourceName", source.getSource_name());
                        startActivity(sourceNewsIntent);
                    }
                }));

        technology.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener(){
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent sourceNewsIntent = new Intent(getActivity(), SourceNewsActivity.class);
                        NewsSource source = (NewsSource) techlist.get(position);
                        sourceNewsIntent.putExtra("url", source.getUrl());
                        sourceNewsIntent.putExtra("sourceName", source.getSource_name());
                        startActivity(sourceNewsIntent);
                    }
                }));

        sports.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener(){
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent sourceNewsIntent = new Intent(getActivity(), SourceNewsActivity.class);
                        NewsSource source = (NewsSource) sportlist.get(position);
                        sourceNewsIntent.putExtra("url", source.getUrl());
                        sourceNewsIntent.putExtra("sourceName", source.getSource_name());
                        startActivity(sourceNewsIntent);
                    }
                }));

        gaming.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener(){
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent sourceNewsIntent = new Intent(getActivity(), SourceNewsActivity.class);
                        NewsSource source = (NewsSource) gamelist.get(position);
                        sourceNewsIntent.putExtra("url", source.getUrl());
                        sourceNewsIntent.putExtra("sourceName", source.getSource_name());
                        startActivity(sourceNewsIntent);
                    }
                }));

        science.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener(){
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent sourceNewsIntent = new Intent(getActivity(), SourceNewsActivity.class);
                        NewsSource source = (NewsSource) sclist.get(position);
                        sourceNewsIntent.putExtra("url", source.getUrl());
                        sourceNewsIntent.putExtra("sourceName", source.getSource_name());
                        startActivity(sourceNewsIntent);
                    }
                }));

        entertainment.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener(){
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent sourceNewsIntent = new Intent(getActivity(), SourceNewsActivity.class);
                        NewsSource source = (NewsSource) enterlist.get(position);
                        sourceNewsIntent.putExtra("url", source.getUrl());
                        sourceNewsIntent.putExtra("sourceName", source.getSource_name());
                        startActivity(sourceNewsIntent);
                    }
                }));

//        GridView source_grid = (GridView) getView().findViewById(R.id.source_grid);
//        SourceAdapter adapter = new SourceAdapter(getActivity(), sources);
//        source_grid.setAdapter(adapter);
//
//        source_grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                Intent sourceNewsIntent = new Intent(getActivity(), SourceNewsActivity.class);
//                sourceNewsIntent.putExtra("url", sources.get(position).getUrl());
//                sourceNewsIntent.putExtra("sourceName", sources.get(position).getSource_name());
//                startActivity(sourceNewsIntent);
//            }
//        });
    }
}
