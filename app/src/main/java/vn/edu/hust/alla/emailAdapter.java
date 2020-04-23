package vn.edu.hust.alla;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class emailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<EmailModel> emails;
    boolean state;

    public emailAdapter(List<EmailModel> emails) {
        this.emails = emails;
    }

    public emailAdapter(List<EmailModel> emails, boolean state) {
        this.emails = emails;
        this.state = state;
    }

    @NonNull
    @Override //tao moi view holder neu chua co
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item, parent,false);
        return new EmailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        EmailViewHolder viewHolder = (EmailViewHolder) holder;
        EmailModel email = emails.get(position);

        viewHolder.imageRound.setColorFilter(Color.parseColor(email.getColor()));
        viewHolder.textRound.setText(email.getName().substring(0,1));
        viewHolder.textName.setText(email.getName());
        viewHolder.textSubject.setText(email.getSubject());
        viewHolder.textContent.setText(email.getContent());
        viewHolder.textTime.setText(email.getTime());
        if(!email.isFavorite()) {
            viewHolder.imageFavorite.setImageResource(R.drawable.ic_star_normal);
        }
        else {
            viewHolder.imageFavorite.setImageResource(R.drawable.ic_star_favorite);
        }

    }

    @Override
    public int getItemCount() {
        return emails.size();
    }

    //tao 1 view holder ke thua tu recycler view holder va ket noi giua view holder - view hien thi
    class EmailViewHolder extends RecyclerView.ViewHolder {

        ImageView imageRound;
        TextView textRound;
        TextView textName;
        TextView textSubject;
        TextView textContent;
        TextView textTime;
        ImageView imageFavorite;

        public EmailViewHolder(@NonNull View itemView) {
            super(itemView);

            imageRound = itemView.findViewById(R.id.image_round);
            textRound = itemView.findViewById(R.id.text_round);
            textName = itemView.findViewById(R.id.text_name);
            textSubject = itemView.findViewById(R.id.text_subject);
            textContent = itemView.findViewById(R.id.text_content);
            textTime = itemView.findViewById(R.id.text_time);
            imageFavorite = itemView.findViewById(R.id.image_favorite);

            imageFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isSelect = emails.get(getAdapterPosition()).isFavorite();
                    emails.get(getAdapterPosition()).setFavorite(!isSelect);

                    if( state ) { //true , dang o trang favorite
                        if ( isSelect )//dang la da favorite an tiep de bo khoi danh sach favorite
                            emails.remove(emails.get(getAdapterPosition()));
                    }

                    notifyDataSetChanged();

                }
            });

        }

    }


}
