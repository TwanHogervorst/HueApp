package student.avansti.hueapp;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import student.avansti.hueapp.data.DLamp;

public class LampAdapter extends RecyclerView.Adapter<LampAdapter.LampViewHolder> {

    private Context AppContext;
    private List<DLamp> lampList;
    private OnItemClickListener clickListener;

    public interface OnItemClickListener{
        void onItemClick(int clickedPosition);
    }

    class LampViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public final TextView textView;
        public final ImageView imageView;

        public LampViewHolder(View itemView, LampAdapter adapter) {
            super(itemView);
            textView = itemView.findViewById(R.id.item_list_textView);
            imageView = itemView.findViewById(R.id.item_list_imageView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            clickListener.onItemClick(clickedPosition);
        }
    }

    public LampAdapter(Context appContext, List<DLamp> lampList, OnItemClickListener clickListener) {
        AppContext = appContext;
        this.lampList = lampList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public LampViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.lamp_list_item,parent,false);
        LampViewHolder viewHolder = new LampViewHolder(itemview,this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LampViewHolder holder, int position) {
        DLamp lamp = lampList.get(position);
        holder.textView.setText(lamp.getCaption());
        int rgb = Color.HSVToColor(new float[] {
                (float)Utility.map(lamp.state.hue, 0, 65535, 0, 360),
                (float)Utility.map(lamp.state.sat,0, 254,0,1),
                (float) Utility.map(lamp.state.bri,1,254,0, 1)
        });
        holder.imageView.setColorFilter(rgb);
    }

    @Override
    public int getItemCount() {
        return lampList.size();
    }
}
