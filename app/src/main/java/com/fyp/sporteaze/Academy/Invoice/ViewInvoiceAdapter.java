package com.fyp.sporteaze.Academy.Invoice;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fyp.sporteaze.Model.Invoice;
import com.fyp.sporteaze.R;

import java.util.List;

public class ViewInvoiceAdapter extends RecyclerView.Adapter{
    List<Invoice> invoiceList;
    String academy_id;
    String academy_email;

    public ViewInvoiceAdapter(List<Invoice> invoiceList, String academy_id, String academy_email) {
        this.invoiceList = invoiceList;
        this.academy_id = academy_id;
        this.academy_email = academy_email;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.invoices_layout_file, parent,false);
//        return null;
        ViewHolderClass viewHolderClass = new ViewHolderClass(view);
        return viewHolderClass;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolderClass viewHolderClass = (ViewHolderClass)holder;
        Invoice invoice = invoiceList.get(position);
        viewHolderClass.recycler_invoice_event.setText(invoice.getEvent());
        viewHolderClass.recycler_invoice_id.setText(invoice.getInvoice_id());
        viewHolderClass.recycler_invoice_total.setText(invoice.getTotal_charges());

    }

    @Override
    public int getItemCount() {
        return invoiceList.size();
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder{

        TextView recycler_invoice_id;
        TextView recycler_invoice_event;
        TextView recycler_invoice_total;
        View view;
//        AppCompatButton btn_coach_view_details;
//        AppCompatButton btn_academy_delete;
        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);


            recycler_invoice_id = itemView.findViewById(R.id.recycler_invoice_id);
            recycler_invoice_event = itemView.findViewById(R.id.recycler_invoice_event);
            recycler_invoice_total = itemView.findViewById(R.id.recycler_invoice_total);

//            users_email = itemView.findViewById(R.id.recylcer_user_email);
//            btn_coach_view_details=itemView.findViewById(R.id.btn_coach_view_details);
//            btn_academy_view_details.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    Intent intent = new Intent( view.getContext(), AdminViewAcademyDetails.class);
//                    intent.putExtra("academy_image", get);
//                    view.getContext().startActivity(intent);
//
//
//                }
//            });

        }
    }


}
