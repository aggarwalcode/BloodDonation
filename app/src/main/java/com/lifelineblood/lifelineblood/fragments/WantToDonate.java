package com.lifelineblood.lifelineblood.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lifelineblood.lifelineblood.R;
import com.lifelineblood.lifelineblood.adapterclass.DisplayNeedRequestToDoner;
import com.lifelineblood.lifelineblood.modelclass.BloodRequesteeDetails;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.lifelineblood.lifelineblood.activities.LoginActivity.auth;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WantToDonate.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WantToDonate#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WantToDonate extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    DatabaseReference databaseReference;
    RecyclerView rvBloodNeedy;
    DisplayNeedRequestToDoner mAdapter;
    List<BloodRequesteeDetails> bloodNeedyList = new ArrayList<>();
    String sdf;             SimpleDateFormat timestamp=null;

    private OnFragmentInteractionListener mListener;

    public WantToDonate() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WantToDonate.
     */
    // TODO: Rename and change types and number of parameters
    public static WantToDonate newInstance(String param1, String param2) {
        WantToDonate fragment = new WantToDonate();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_donate_blood, container, false);
        rvBloodNeedy = view.findViewById(R.id.rvBloodNeedy);
        rvBloodNeedy.setLayoutManager(new LinearLayoutManager(getContext()));
        rvBloodNeedy.addItemDecoration(new DividerItemDecoration(
                rvBloodNeedy.getContext(), DividerItemDecoration.VERTICAL));

        timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
        sdf = timestamp.format(Calendar.getInstance().getTime());

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("users").child(auth.getUid()).child("isDoner").setValue("true");
        databaseReference.child("users").child(auth.getUid()).child("lastClickOnDonate").setValue(sdf);
        databaseReference.child("needRequests").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    BloodRequesteeDetails bloodRequesteeDetails = postSnapshot.getValue(BloodRequesteeDetails.class);
                    bloodNeedyList.add(bloodRequesteeDetails);
                    mAdapter = new DisplayNeedRequestToDoner(getContext(), bloodNeedyList);
                    rvBloodNeedy.setAdapter(mAdapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
