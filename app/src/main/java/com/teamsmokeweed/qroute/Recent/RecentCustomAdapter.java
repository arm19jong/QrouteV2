package com.teamsmokeweed.qroute.Recent;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.squareup.picasso.Picasso;
import com.teamsmokeweed.qroute.Content;
import com.teamsmokeweed.qroute.R;
import com.teamsmokeweed.qroute.firebase.CenteridValue;

import java.util.Arrays;
import java.util.List;

/**
 * Created by jongzazaal on 9/11/2559.
 */

public class RecentCustomAdapter extends RecyclerSwipeAdapter<RecentCustomAdapter.ViewHolder> {
    Context context;
    private List<CenteridValue> mDataSet;

    public void clearData() {
        mDataSet.removeAll(mDataSet);
        //mAdapter.notifyDataSetChanged();
        notifyDataSetChanged();
        //recyclerView.setAdapter(mAdapter);
    }


    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
//        TextView mTitle, mPlaceName, tvDel;
        public SwipeLayout swipeLayout;
        public ImageView picView;
        public TextView titles;
        CardView cardView;

        public ViewHolder(View v) {
            super(v);
//            mTitle = (TextView) v.findViewById(R.id.titles);
//            mPlaceName = (TextView) v.findViewById(R.id.placeName);
            cardView = (CardView) v.findViewById(R.id.card_view);
            //tvDel = (TextView) v.findViewById(R.id.tvDelete);
            picView = (ImageView) v.findViewById(R.id.picView);
            titles = (TextView) v.findViewById(R.id.titles);
            itemView.setTag(itemView);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
        }

    }

    public RecentCustomAdapter(Context context, List<CenteridValue> mDataSet){
        this.context = context;
        this.mDataSet = mDataSet;

    }

    @Override
    public RecentCustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_text_view, parent, false);
        // set the view's size, margins, paddings and layout parameters

        RecentCustomAdapter.ViewHolder vh = new RecentCustomAdapter.ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(final RecentCustomAdapter.ViewHolder viewHolder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final CenteridValue centeridValue = mDataSet.get(position);
        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);

        // Drag From Left
        //viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, viewHolder.swipeLayout.findViewById(R.id.bottom_wrapper1));

        // Drag From Right
        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, viewHolder.swipeLayout.findViewById(R.id.bottom_wrapper));

//        viewHolder.mTitle.setText(valuesF.getTitles());
//        viewHolder.mPlaceName.setText(valuesF.getPlaceName());
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> sqr = Arrays.asList(
                        centeridValue.getLat().toString(),
                        centeridValue.getLng().toString(),
                        centeridValue.getTitles(),
                        centeridValue.getPlaceName(),
                        centeridValue.getPlaceType(),
                        centeridValue.getDes(),
                        centeridValue.getWeb(),
                        centeridValue.getPic(),
                        centeridValue.getStart_date(),
                        centeridValue.getStart_time(),
                        centeridValue.getEnd_date(),
                        centeridValue.getEnd_time()
                );
                startResultActivity(context, sqr);
            }
        });

        viewHolder.titles.setText(centeridValue.getTitles());
        Picasso.with(context)
                .load(centeridValue.getPic())
                //.resize(30,30)
                .placeholder(R.drawable.ic_hourglass_empty_24dp)
                .error(R.drawable.ic_hourglass_empty_24dp)
                .into(viewHolder.picView);
//        viewHolder.tvDel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String itemLabel = valuesF.getTitles();
//
//                mDataSet.remove(position);
//                DellDatabase dellDatabase = new DellDatabase();
//                dellDatabase.del(valuesF.getPid());
//
//                // Remove the item on remove/button click
////                DateQr dateQr = new DateQr();
////                dateQr.setId(arrayList_Id.get(position));
////
////                DelDatabaseQr delDatabaseQr = new DelDatabaseQr(dateQr, context);
////                delDatabaseQr.DelDb();
//
//
////                arrayList_Title.remove(position);
////                arrayList_Lat.remove(position);
////                arrayList_Lng.remove(position);
////                arrayList_PlaceName.remove(position);
////                arrayList_PlaceType.remove(position);
////                arrayList_Des.remove(position);
////                arrayList_WebPage.remove(position);
////                arrayList_Id.remove(position);
//
//
//
//                notifyItemRemoved(position);
//
//                notifyItemRangeChanged(position, mDataSet.size());
//
//                // Show the removed item label
//                Toast.makeText(context,"Removed : " + itemLabel,Toast.LENGTH_SHORT).show();
//            }
//        });

        mItemManger.bindView(viewHolder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public static void startResultActivity(Context context, List<String> sqr) {
        Intent intent = new Intent(context, Content.class);
        //String[] sqr = sqr;
        //ArrayList<String> sqrr = new ArrayList<String>(sqr);
        String[] sqrr = (String[]) sqr.toArray(new String[sqr.size()]);
        intent.putExtra("sQr", sqrr);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}