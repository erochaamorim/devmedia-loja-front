package app.devmedia.com.br.appdevmedia.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.List;

import app.devmedia.com.br.appdevmedia.R;
import app.devmedia.com.br.appdevmedia.adapter.ProdutoRecyclerAdapter;
import app.devmedia.com.br.appdevmedia.entidades.Produto;
import app.devmedia.com.br.appdevmedia.util.Constantes;

/**
 * Created by erick.amorim on 25/09/2017.
 */

public class FragmentProdutos extends Fragment {

    protected RecyclerView rv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_produtos, container, false);
        rv = (RecyclerView) view.findViewById(R.id.rvProdutos);
        rv.setLayoutManager(new LinearLayoutManager(getContext() ) );
        final RelativeLayout lytLoading = (RelativeLayout) view.findViewById(R.id.lytLoading);
        lytLoading.setVisibility(View.VISIBLE);
        new AsyncHttpClient().get(Constantes.URL_WS_BASE+"produtos/index", new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                if(response != null) {

                    Type type = new TypeToken<List<Produto>>() {}.getType();
                    List<Produto> produtos = new Gson().fromJson(response.toString(), type);
                    ProdutoRecyclerAdapter adapter = new ProdutoRecyclerAdapter(produtos);
                    rv.setAdapter(adapter);

                } else {

                    Toast.makeText(getActivity(), "Houve um erro no carregamento da lista de produtos.", Toast.LENGTH_SHORT).show();

                }
                lytLoading.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(getActivity(), "Falha: "+responseString, Toast.LENGTH_LONG).show();
            }
        });
        return view;

    }
}
