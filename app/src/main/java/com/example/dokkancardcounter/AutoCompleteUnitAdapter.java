package com.example.dokkancardcounter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AutoCompleteUnitAdapter extends ArrayAdapter<UnitItem> {

    private List<UnitItem> unitListFull;

    public AutoCompleteUnitAdapter(@NonNull Context context, @NonNull List<UnitItem> unitList) {
        super(context, 0, unitList);
        unitListFull = new ArrayList<>(unitList);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return unitFilter;
    }

    //Grab the information from the lists and display them in a each row
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.unit_autocomplete_row, parent, false);
        }
        TextView textViewName = convertView.findViewById(R.id.unitName_text);
        ImageView imageView = convertView.findViewById(R.id.unitPic_image);
        UnitItem unitItem = getItem(position);
        if (unitItem != null) {
            textViewName.setText(unitItem.getUnitName());
            Picasso.get().load(unitItem.getUnitPicture()).into(imageView);
        }
        return convertView;
    }

    //Filter out the list as the user specifies which unit he/she wants to select
    private Filter unitFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            List<UnitItem> suggestions = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(unitListFull);
            } else {
                String filerPattern = constraint.toString().toLowerCase().trim();
                for (UnitItem item : unitListFull) {
                    if (item.getUnitName().toLowerCase().contains(filerPattern)) {
                        suggestions.add(item);
                    }
                }
            }
            filterResults.values = suggestions;
            filterResults.count = suggestions.size();
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List) results.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((UnitItem) resultValue).getUnitName();
        }
    };
}
