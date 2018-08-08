package com.lifelineblood.lifelineblood.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lifelineblood.lifelineblood.R;

import static com.lifelineblood.lifelineblood.activities.LoginActivity.auth;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RequestingForBlood.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RequestingForBlood#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RequestingForBlood extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static TextView need_response_text;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    static String isNeedy;

    DatabaseReference databaseReference;

    private OnFragmentInteractionListener mListener;

    public RequestingForBlood() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RequestingForBlood.
     */
    // TODO: Rename and change types and number of parameters
    public static RequestingForBlood newInstance(String param1, String param2) {
        RequestingForBlood fragment = new RequestingForBlood();
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

        View view = inflater.inflate(R.layout.fragment_need_blood, container, false);

        need_response_text = view.findViewById(R.id.frag_need_text);

        databaseReference = FirebaseDatabase.getInstance().getReference().
                child("users").child(auth.getUid()).child("isNeedy");

        readData(new MyCallback() {
            @Override
            public void onCallback(String isNeedyVal) {
                assert isNeedyVal!=null;
                if (isNeedyVal.equals("false") || isNeedy.equals("")) {

                    Fragment fragment = new RequestForm();
                    FragmentManager manager = getFragmentManager();
                    assert manager!=null;
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.add(R.id.container, fragment, "RequestForm");
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                else {
                    need_response_text.setText(R.string.already_requested);
                }
            }
        });

        return view;
    }

    public interface MyCallback {
        void onCallback(String value);
    }

    public void readData(final MyCallback myCallback) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                isNeedy = String.valueOf(dataSnapshot.getValue());
                assert isNeedy!=null;
                myCallback.onCallback(isNeedy);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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

    public boolean allowBackPressed() {
        return true;
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
