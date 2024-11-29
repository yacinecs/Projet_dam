package com.example.projetdam;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ToggleButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListeEmployeeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListeEmployeeFragment extends Fragment {

    private ToggleButton toggleButton;
    private RecyclerView.LayoutManager layoutManager;
    private AdapterListContact adapter;
    //static ArrayList<Employee> list = new ArrayList<>();
    //DataBaseHelper.list
    DataBaseHelper dataBaseHelper ;
    ListeOfEmployee e;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView recyclerView ;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListeEmployeeFragment() {
    }
    public ListeEmployeeFragment(ListeOfEmployee e ) {
        this.e = e ;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListeEmployeeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListeEmployeeFragment newInstance(String param1, String param2) {
        ListeEmployeeFragment fragment = new ListeEmployeeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_liste_employee, container, false);
        dataBaseHelper = DataBaseHelper.GetDataBaseHelper(this.getContext());
        recyclerView = view.findViewById(R.id.recycler_view);
        toggleButton = view.findViewById(R.id.toggleButton);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        AdapterListContact adapter = new AdapterListContact(dataBaseHelper.list,this);
        recyclerView.setAdapter(adapter);

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Switch to GridLayoutManager
                    layoutManager = new GridLayoutManager(getContext(), 2); // Change the span count as needed
                } else {
                    // Switch to LinearLayoutManager
                    layoutManager = new LinearLayoutManager(getContext());
                }
                recyclerView.setLayoutManager(layoutManager);
            }
        });



        EditText rech = view.findViewById(R.id.recherch);
        rech.addTextChangedListener( new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<Employee> newList = new ArrayList<>();

                for (Employee e : dataBaseHelper.list) {
                    if (e.getNom().toLowerCase().startsWith(s.toString().toLowerCase())) {
                        newList.add(e);
                    }
                }
                AdapterListContact adapter = new AdapterListContact(newList,ListeEmployeeFragment.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                AdapterListContact adapter = new AdapterListContact(dataBaseHelper.list,ListeEmployeeFragment.this);
                recyclerView.setAdapter(adapter);
                swipeRefreshLayout.setRefreshing(false);
            }
        });


        return view;
    }
    public void modification(int position){
        e.modification(position);
    }
    public void Profile(int position){
        e.Profil(position);
    }
    public void Suppression(int position, View view){
        Employee id = dataBaseHelper.list.get(position);
        dataBaseHelper.deleteEmployee(id.getId());
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        AdapterListContact adapter = new AdapterListContact(dataBaseHelper.list,this);
        recyclerView.setAdapter(adapter);
    }

    public void call(String numero) {
        Intent intent2 = new Intent(Intent.ACTION_DIAL);
        intent2.setData(Uri.parse("tel:"+numero));
        startActivity(intent2);
    }
}