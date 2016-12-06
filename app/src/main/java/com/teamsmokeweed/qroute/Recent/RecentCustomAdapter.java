package com.teamsmokeweed.qroute.Recent;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.squareup.picasso.Picasso;
import com.teamsmokeweed.qroute.Content;
import com.teamsmokeweed.qroute.R;
import com.teamsmokeweed.qroute.firebase.CenteridValue;
import com.teamsmokeweed.qroute.firebase.DellDatabase;
import com.teamsmokeweed.qroute.notification.DataSetNoti;
import com.teamsmokeweed.qroute.notification.SampleAlarmReceiver;
import com.tomerrosenfeld.customanalogclockview.CustomAnalogClock;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by jongzazaal on 9/11/2559.
 */

public class RecentCustomAdapter extends RecyclerSwipeAdapter<RecentCustomAdapter.ViewHolder> {
    Context context;
    private List<CenteridValue> mDataSet;
    private List<String> mMobileID;
    private String mid;
    RecyclerView.Adapter adapter;
    SampleAlarmReceiver sampleAlarmReceiver = new SampleAlarmReceiver();


    public void clearData() {
        mDataSet.removeAll(mDataSet);
//        for (String i:mMobileID){
//            mMobileID.remove(i);
//        }
        mMobileID.clear();
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
        RelativeLayout tvDel;
        public SwipeLayout swipeLayout;
        public ImageView picView;
        public TextView titles, textDay, textMonth, analogClockText;
        CardView cardView;
        CustomAnalogClock customAnalogClock;

        public ViewHolder(View v) {
            super(v);
//            mTitle = (TextView) v.findViewById(R.id.titles);
//            mPlaceName = (TextView) v.findViewById(R.id.placeName);
            cardView = (CardView) v.findViewById(R.id.card_view);
            tvDel = (RelativeLayout) v.findViewById(R.id.Del);
            picView = (ImageView) v.findViewById(R.id.picView);
            titles = (TextView) v.findViewById(R.id.titles);
            textDay = (TextView) v.findViewById(R.id.day);
            textMonth = (TextView) v.findViewById(R.id.month);
            customAnalogClock = (CustomAnalogClock) v.findViewById(R.id.analog_clock);
            analogClockText = (TextView) v.findViewById(R.id.analog_clock_text);
            itemView.setTag(itemView);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);

        }

    }

    public RecentCustomAdapter(Context context, List<CenteridValue> mDataSet, List<String> mMobileID, String mid){
        this.context = context;
        this.mDataSet = mDataSet;
        this.mMobileID = mMobileID;
        this.mid = mid;

    }

    public void swap(List<CenteridValue> mDataSetL){
        if (mDataSet != null) {
            mDataSet.clear();
            mDataSet=mDataSetL;
        }
        else {
            mDataSet=mDataSetL;
        }
        notifyDataSetChanged();
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
        final String mobileID = mMobileID.get(position);

//        String day = centeridValue.getStart_date();
        String[] date = centeridValue.getStart_date().split("-");
        String[] time = centeridValue.getStart_time().split(":");

        int day = Integer.parseInt(date[2]);
        int month = Integer.parseInt(date[1]);
        int year = Integer.parseInt(date[0]);
        int hour = Integer.parseInt(time[0]);
        int minute = Integer.parseInt(time[1]);
//        String hour = centeridValue.getStart_date();
//        DataSetNoti dataSetNoti = new DataSetNoti(1,2,3);

//        Toast.makeText(context, ""+day+"/"+month+"/"+year, Toast.LENGTH_SHORT).show();
        final List<String> sqr = Arrays.asList(
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

        DataSetNoti.getInstance().SetDateTime(day, month, year, hour, minute, position, (String[]) sqr.toArray(new String[sqr.size()]));
//        SampleAlarmReceiver sampleAlarmReceiver = new SampleAlarmReceiver();
        SampleAlarmReceiver.timeMillis = DataSetNoti.getInstance().getTimeInMillis();
        SampleAlarmReceiver.position = DataSetNoti.getInstance().getPosition();



//        String[] sqrr = (String[]) sqr.toArray(new String[sqr.size()]);



        sampleAlarmReceiver.setAlarm(context);
//        customAnalogClock.setAutoUpdate(false);
//        customAnalogClock.setTime(calendar);

        viewHolder.customAnalogClock.setAutoUpdate(false);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time[0]));
        c.set(Calendar.MINUTE, Integer.parseInt(time[1]));
        viewHolder.customAnalogClock.setTime(c);

        String ampm = "P.M.";
        if (Integer.parseInt(time[0])<=11){
            ampm = "A.M.";
        }

        viewHolder.analogClockText.setText(ampm);
        viewHolder.titles.setText(centeridValue.getTitles());

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
//                List<String> sqr = Arrays.asList(
//                        centeridValue.getLat().toString(),
//                        centeridValue.getLng().toString(),
//                        centeridValue.getTitles(),
//                        centeridValue.getPlaceName(),
//                        centeridValue.getPlaceType(),
//                        centeridValue.getDes(),
//                        centeridValue.getWeb(),
//                        centeridValue.getPic(),
//                        centeridValue.getStart_date(),
//                        centeridValue.getStart_time(),
//                        centeridValue.getEnd_date(),
//                        centeridValue.getEnd_time()
//                );
                startResultActivity(context, sqr);
            }
        });

        Calendar startTime = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            startTime.setTime(sdf.parse(centeridValue.getStart_date()));// all done
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMM");
        String sDay = dayFormat.format(startTime.getTime());
        String sMonth = monthFormat.format(startTime.getTime());
        viewHolder.textDay.setText(sDay);
        viewHolder.textMonth.setText(sMonth);

        Picasso.with(context)
                .load(centeridValue.getPic())
                //.resize(30,30)
                .placeholder(R.drawable.ic_hourglass_empty_24dp)
                .error(R.drawable.ic_hourglass_empty_24dp)
                .into(viewHolder.picView);
        viewHolder.tvDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "click"+mobileID, Toast.LENGTH_SHORT).show();
                new DellDatabase().del(mid, mobileID);
                mDataSet.remove(position);
                mMobileID.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mDataSet.size());
//                SampleAlarmReceiver sampleAlarmReceiver1 = new SampleAlarmReceiver();
                sampleAlarmReceiver.cancelAlarm(context);
//                adapter = RecentCustomAdapter.this;
//                adapter.notifyDataSetChanged();
//                swap(mDataSet);


            }
        });
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