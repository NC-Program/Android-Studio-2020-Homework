package com.example.songlist;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentWebpage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentWebpage extends Fragment {
    private WebView webView;

    private static final String ARG_PARAM1 = "param1";


    // TODO: Rename and change types of parameters
    private Song songChosen;

    public FragmentWebpage() {
        // Required empty public constructor
    }

    public static FragmentWebpage newInstance(Song song) {
        FragmentWebpage fragment = new FragmentWebpage();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1,song);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            songChosen = getArguments().getParcelable(ARG_PARAM1);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_webpage, container, false);
        webView=view.findViewById(R.id.webView);
        final ProgressDialog progressDialog = new ProgressDialog(container.getContext());
        progressDialog.setMessage("Loading Data...");
        progressDialog.setCancelable(false);
        WebView web_view = view.findViewById(R.id.webView);
        web_view.requestFocus();
        web_view.getSettings().setJavaScriptEnabled(true);
        web_view.getSettings().setGeolocationEnabled(true);
        web_view.setSoundEffectsEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(songChosen.getWebsite());
        web_view.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress < 100) {
                    progressDialog.show();
                }
                if (progress == 100) {
                    progressDialog.dismiss();
                }
            }
        });
        return view;
    }
}