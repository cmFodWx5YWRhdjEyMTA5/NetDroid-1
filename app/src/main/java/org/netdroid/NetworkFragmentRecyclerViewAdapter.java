package org.netdroid;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Michał on 05.06.2018.
 */

public class NetworkFragmentRecyclerViewAdapter extends RecyclerView.Adapter<NetworkFragmentRecyclerViewAdapter.RViewHolder> {
    Context context;
    ArrayList<String> ipList;
    ArrayList<String> macList;
    public NetworkFragmentRecyclerViewAdapter(Context context, ArrayList<String> ipList, ArrayList<String> macList){
        this.context=context;
        this.ipList=ipList;
        this.macList=macList;
    }
    @Override
    public RViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater rvLayoutInfalter=LayoutInflater.from(context);
        View adapterVievObject=rvLayoutInfalter.inflate(R.layout.network_fragment_recycler_view_layout,parent,false);
        return new RViewHolder(adapterVievObject);
    }

    @Override
    public void onBindViewHolder(RViewHolder holder, int position) {
    holder.t1.setText(ipList.get(position));
    holder.t2.setText(macList.get(position));

    }

    @Override
    public int getItemCount() {
        return ipList.size();
    }

    public class RViewHolder extends RecyclerView.ViewHolder{
        TextView t1,t2;
        public RViewHolder(View itemView) {
            super(itemView);
        t1=(TextView)itemView.findViewById(R.id.rvIpAddress);
        t2=(TextView)itemView.findViewById(R.id.rvMacAddress);

        }
    }
}
