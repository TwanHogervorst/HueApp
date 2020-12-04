package student.avansti.hueapp.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import student.avansti.hueapp.R;
import student.avansti.hueapp.data.DLamp;
import student.avansti.hueapp.viewmodels.LampViewModel;

public class LampAdapter extends RecyclerView.Adapter<LampAdapter.LampViewHolder> {

    private LampViewModel lampVM;

    class LampViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView textView;
        private final ImageView imageView;

        private DLamp lamp;

        public LampViewHolder(View itemView, LampAdapter adapter) {
            super(itemView);
            textView = itemView.findViewById(R.id.item_list_textView);
            imageView = itemView.findViewById(R.id.item_list_imageView);
            itemView.setOnClickListener(this);
        }

        public void setLamp(DLamp lamp) {
            this.lamp = lamp;

            this.textView.setText(this.lamp.name);
            this.imageView.setColorFilter(this.lamp.state.getColor().asAndroidColor().toArgb());

            if(this.lamp.state.on) this.imageView.setImageResource(R.drawable.ic_lamp_on);
            else this.imageView.setImageResource(R.drawable.ic_lamp_off);
        }

        @Override
        public void onClick(View v) {
            lampVM.select(this.lamp);
        }
    }

    public LampAdapter(LampViewModel lampVM) {
        this.lampVM = lampVM;
    }

    @NonNull
    @Override
    public LampViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.lamp_list_item, parent,false);
        LampViewHolder viewHolder = new LampViewHolder(itemview,this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LampViewHolder holder, int position) {
        DLamp lamp = this.lampVM.getLamps().getValue().get(position);
        holder.setLamp(lamp);
    }

    @Override
    public int getItemCount() {
        return this.lampVM.getLamps().getValue().size();
    }
}
