package com.teamsmokeweed.qroute;

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

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by jongzazaal on 2/11/2559.
 */

public class MyAdapter extends RecyclerSwipeAdapter<MyAdapter.ViewHolder> {
    private ArrayList<String> mDataset;
    private Context mContext;

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder{
        // each data item is just a string in this case
        public TextView titles;

        public ImageView imageView;
        public SwipeLayout swipeLayout;
        //public TextView tvDelete;
        public CardView cardView;
        public ViewHolder(View v) {
            super(v);
            imageView = (ImageView) v.findViewById(R.id.picView);
            titles = (TextView) v.findViewById(R.id.titles);
            //tvEdit = (TextView) itemView.findViewById(R.id.tvEdit);
            //tvDelete = (TextView) itemView.findViewById(R.id.tvDelete);
            cardView = (CardView) v.findViewById(R.id.card_view);
            itemView.setTag(itemView);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);



        }

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(Context contexts ,ArrayList<String> myDataset) {
        mDataset = myDataset;
        this.mContext = contexts;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_text_view, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new MyAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyAdapter.ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);

        // Drag From Left
       //holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, holder.swipeLayout.findViewById(R.id.bottom_wrapper1));

        // Drag From Right
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, holder.swipeLayout.findViewById(R.id.bottom_wrapper));

        holder.titles.setText(mDataset.get(position));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivityB(mContext,mDataset.get(position));
            }
        });

//        holder.tvEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//                Toast.makeText(view.getContext(), "Clicked on Edit  " + holder.mTextView.getText().toString(), Toast.LENGTH_SHORT).show();
//            }
//        });

//        holder.tvEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //startActivityB(mContext, mDataset.get(position), mpicDateset.get(position), mphoneDataset.get(position), memailDataset.get(position));
//                Toast.makeText(mContext, "#" + position + " - " + mDataset.get(position), Toast.LENGTH_SHORT).show();
//            }
//        });

//        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mItemManger.removeShownLayouts(holder.swipeLayout);
//                mDataset.remove(position);
//                mpicDateset.remove(position);
//                memailDataset.remove(position);
//                mphoneDataset.remove(position);
//                notifyItemRemoved(position);
//                notifyItemRangeChanged(position, mDataset.size());
//                mItemManger.closeAllItems();
//            }
//        });

        //holder.mTextView.setText(mDataset.get(position));
//        holder.mTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivityB(mContext, mDataset.get(position), mpicDateset.get(position), mphoneDataset.get(position), memailDataset.get(position));
//            }
//        });
//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //startActivityB(mContext, mDataset.get(position), mpicDateset.get(position), mphoneDataset.get(position), memailDataset.get(position));
//            }
//        });

        Picasso.with(mContext)
                .load(mDataset.get(position))
                //.resize(30,30)
                .placeholder(R.drawable.ic_hourglass_empty_24dp)
                .error(R.drawable.ic_hourglass_empty_24dp)
                .into(holder.imageView);

        mItemManger.bindView(holder.itemView, position);
    }

    // Replace the contents of a view (invoked by the layout manager)

    public static void startActivityB(Context context, String web) {
        Intent intent = new Intent(context, Content.class);
        intent.putExtra("web",web);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }



}
