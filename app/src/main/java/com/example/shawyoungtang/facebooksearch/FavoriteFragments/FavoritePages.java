package com.example.shawyoungtang.facebooksearch.FavoriteFragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.shawyoungtang.facebooksearch.R;
import com.example.shawyoungtang.facebooksearch.Utils.Cons;
import com.example.shawyoungtang.facebooksearch.Utils.DetailAsynTask;
import com.example.shawyoungtang.facebooksearch.Utils.FavoriteAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ShawYoungTang on 4/9/2017.
 */

public class FavoritePages extends Fragment {

    View view;
    ArrayList<String> pageIDs;
    ArrayList<String> headPhotoUrls;
    ArrayList<String> names;
    private boolean notFirstLoad = false;
    FavoriteAdapter adapter;
    ListView listView;
    HashMap<String, String> idMap;
    HashMap<String, String> urlMap;
    HashMap<String, String> nameMap;
    HashMap<String, String> timestamps;


    public  FavoritePages() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onResume() {
        super.onResume();
        if (notFirstLoad){
            updateList();
            getFragmentManager().beginTransaction().detach(this).commit();
            notFirstLoad = false;
            getFragmentManager().beginTransaction().attach(this).commit();

        }else{
            notFirstLoad = true;
        }
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.favorite_fragments, container, false);

        updateList();


        return view;
    }


    private void updateList(){
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(Cons.FAVORITES, 0);
        HashMap<String, ?> map = (HashMap<String, ?>) sharedPreferences.getAll();

        pageIDs = new ArrayList<>();
        headPhotoUrls = new ArrayList<>();
        names = new ArrayList<>();
        idMap = new HashMap<>();
        urlMap = new HashMap<>();
        nameMap = new HashMap<>();
        timestamps = new HashMap<>();


        for (Map.Entry<String, ?> e : map.entrySet()){
            if (isPage(e.getKey())){
                String key = e.getKey();
//                pageIDs.add((String) map.get(key));
//                headPhotoUrls.add((String) map.get(Cons.PHOTO_URL + key));
//                names.add((String) map.get(Cons.NAME + key));
                idMap.put(key, (String) map.get(key));
                urlMap.put(key, (String) map.get(Cons.PHOTO_URL + key));
                nameMap.put(key, (String) map.get(Cons.NAME + key));
                timestamps.put(key, (String) map.get(Cons.TIMESTAMP + key));
            }
        }


        // sort
        ArrayList<Map.Entry<String, String>> list = new ArrayList<>(timestamps.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, String>>() {
            @Override
            public int compare(Map.Entry<String, String> e1, Map.Entry<String, String> e2) {
                if (e1.getValue().compareTo(e2.getValue()) > 0)
                    return 1;
                else if (e1.getValue().compareTo(e2.getValue()) < 0)
                    return -1;
                return 0;
            }
        });

        for (Map.Entry<String, String> e : list){
            pageIDs.add(idMap.get(e.getKey()));
            headPhotoUrls.add(urlMap.get(e.getKey()));
            names.add(nameMap.get(e.getKey()));
        }


        adapter = new FavoriteAdapter(this.getActivity(), headPhotoUrls, names);

        listView = (ListView) view.findViewById(R.id.favorite_fragment_list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DetailAsynTask da = new DetailAsynTask(view.getContext(), headPhotoUrls.get(position), names.get(position));
                da.execute(pageIDs.get(position));
            }
        });
    }

    private boolean isPage(String unitID){
        int i = 0;
        while(!Character.isDigit(unitID.charAt(i))){
            i++;
        }
        if (unitID.substring(0, i).equals(Cons.PAGE))
            return true;
        return false;
    }

}
