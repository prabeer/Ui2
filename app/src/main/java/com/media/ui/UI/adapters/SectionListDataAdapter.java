package com.media.ui.UI.adapters;

/**
 * Created by pratap.kesaboyina on 24-12-2014.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.media.ui.R;

import com.bumptech.glide.Glide;
import com.media.ui.UI.models.SingleItemModel;


import java.util.ArrayList;

import static com.media.ui.Util.logger.logg;


public class SectionListDataAdapter extends RecyclerView.Adapter<SectionListDataAdapter.SingleItemRowHolder> {

    private ArrayList<SingleItemModel> itemsList;

   public static ArrayList urlList;
    public static ArrayList pack_name;

    private Context mContext;


    public void Add_to_list(String a){

        urlList.add(a);
    }

    public void Remove_from_list(String a){
        urlList.remove(a);
    }

    public void Add_to_list1(String a){

        pack_name.add(a);
    }

    public void Remove_from_list1(String a){
        pack_name.remove(a);
    }

    public SectionListDataAdapter(Context context, ArrayList<SingleItemModel> itemsList) {
        this.itemsList = itemsList;

        this.mContext = context;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_single_card, null);

        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(final SingleItemRowHolder holder, int i) {

        final SingleItemModel singleItem = itemsList.get(i);

        holder.tvTitle.setText(singleItem.getName());

        urlList = new ArrayList<>();
        pack_name = new ArrayList<>();


        //    SingleItemModel singleItem1 = itemsList.get(i);
      //  holder.itemImage.setImageBitmap(singleItem.getImage());

        Glide.with(mContext)
                .load(singleItem.getUrl())
                .centerCrop()
                .placeholder(R.drawable.android)
                .error(R.drawable.android)
                .into(holder.itemImage);


        holder.itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(v.getContext(), "This will show App description ", Toast.LENGTH_SHORT).show();
            }
        });

        holder.itemcheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked) {
                    Add_to_list( singleItem.getAppURL());
                    Add_to_list1( singleItem.getAppPackage());
                    Toast.makeText(buttonView.getContext(), "Size of urlList" + urlList.size(), Toast.LENGTH_SHORT).show();
                    logg("Size of urlList" + urlList.toString());
                 //   logg("Size of urlList" + urlList.get(1).toString());
                    logg("Size of urlList" + pack_name.toString());
                }

                else{
                    Remove_from_list(singleItem.getAppURL());
                    Remove_from_list1(singleItem.getAppPackage());
                    Toast.makeText(buttonView.getContext(), "Size of urlList" + urlList.size(), Toast.LENGTH_SHORT).show();
                    logg("Size of urlList" + urlList.toString());
                    logg("Size of urlList" + pack_name.toString());
                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView tvTitle;

        protected ImageView itemImage;

        protected  CheckBox itemcheckbox;


        public SingleItemRowHolder(View view) {
            super(view);

            this.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            this.itemImage = (ImageView) view.findViewById(R.id.itemImage);
            this.itemcheckbox = (CheckBox)view.findViewById(R.id.checkboxx);
           //this.itemImage= new ImageDownloader((ImageView) view.findViewById(R.id.itemImage)).execute("http://192.168.43.166/face.png");


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    switch (v.getId()) {

                        case R.id.itemImage:
                            Toast.makeText(v.getContext(), "This will show App description ", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.checkboxx:


                    }
                }
            });


        }

    }

}