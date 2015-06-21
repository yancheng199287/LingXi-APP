package com.example.lxvoip;





import java.util.ArrayList;

import com.example.application.MyApplication;
import com.example.entity.ContactPerson;

import com.example.lxvoip.adapter.ContactAdapter;
import com.example.lxvoip.diyview.QuickAlphabeticBar;
import com.example.lxvoip.utils.IsStrType;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ContactFragment extends Fragment {
  EditText inputSearch;
  ListView contactListView;
  View vv;
	MyApplication app; 
	
	ContactAdapter cba;

QuickAlphabeticBar alpha;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 vv=inflater.inflate(R.layout.contact, container, false);
		 
		 init();
		 
		 
		 contactListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			
			//	TextView tv=(TextView) view.findViewById(R.id.contactNum);
				String num=MyApplication.pList.get(position).getPhoneNumber();
			Toast.makeText(getActivity(), "�����ˣ�"+num, 3000).show();	
				new DialerFragment().call(num);
				
			}
		});
		 
		 
		 
		 
		return vv;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
 public void init(){
	 View header =getActivity(). getLayoutInflater().inflate(R.layout.contact_header_my, null);

	 inputSearch=(EditText) vv.findViewById(R.id.inputSearch);
	 //Ϊ�����༭��������ݸı��¼�
	 searchContact(inputSearch);
	 
	 
	 contactListView=(ListView)vv.findViewById(R.id.contactListView);
	 
	 contactListView.addHeaderView(header);
	 
	alpha=(QuickAlphabeticBar)vv.findViewById(R.id.fast_scroller);
	
	app = (MyApplication) getActivity().getApplicationContext(); 
	filldata();
 }
 
 
 public void filldata(){
		
		
		
		if(app.pList.size()>0){
		
			System.out.println("ȡ����ϵ������"+app.pList.size());
			
			 cba=new ContactAdapter(getActivity(), app.pList, alpha);
				
			contactListView.setAdapter(cba);
			
			//���ұ���ĸ������������ԣ���һ�����������Գ�ʼ��
		
			alpha.init(vv);
			
			//��һ����ϵ��listview����
			alpha.setListView(contactListView);
			
			//���ø߶�
		//	alpha.setHight(alpha.getHeight());
			//������ʾ
			alpha.setVisibility(View.VISIBLE);
					
		}else{
	
			System.out.println("meiyou��ϵ������");
		}

		
}
	
	
	
	
 
	public void searchContact(EditText et){
		
		et.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			String str=s.toString();
			
			//�жϵ�ǰ��������� ������ĸ�������ġ����������Ƕ�֧������
			//��������־���ƥ��绰���룬�������ĸ�Ǿ���pinyin������Ǽ�ƴ����FormattedNumber��ʱ������
			int key=IsStrType.tell(str);
			switch (key) {
			case 0:
				break;
			case 1:
				System.out.println("������������֣�ƥ��绰����");
				findByNumber();
				break;
			case 2:
				System.out.println("�����������ĸ��ƥ��pinyin");
				 findByPinyin();
				break;
			case 3:
				System.out.println("������������ģ�ƥ������");
				findByChinese();
				break;
			default:
				break;
			}
				
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	
	
	//ƥ�����֣������ҵ��绰�������
	ArrayList<ContactPerson> newList=new ArrayList<ContactPerson>();
	public void findByNumber(){
		newList.clear();
		String co=inputSearch.getText().toString();
		System.out.println("��ǰ�������ַ����ǣ�"+co);
		
		for (int i = 0; i <app.pList.size(); i++) {
			
			if(app.pList.get(i).getPhoneNumber().indexOf(co)!=-1 || (app.pList.get(i).getFormattedNumber().indexOf(co)!=-1)){
				
				System.out.println("��ǰƥ��ĵ绰�����ǣ�"+app.pList.get(i).getPhoneNumber());
				newList.add(app.pList.get(i));		
			}	
		}
		cba.pList=newList;
		cba.notifyDataSetChanged();
		
	}
	
	//����ƴ������
	public void findByPinyin(){
		newList.clear();
		String co=inputSearch.getText().toString();
		System.out.println("��ǰ�������ַ����ǣ�"+co);
		
		for (int i = 0; i <app.pList.size(); i++) {
			
			if(app.pList.get(i).getPinyin().toLowerCase().indexOf(co)!=-1|| app.pList.get(i).getSortkey().toLowerCase().indexOf(co)!=-1){
				System.out.println("��ǰƥ���ƴ���ǣ�"+app.pList.get(i).getPinyin());
				newList.add(app.pList.get(i));		
			}	
		}
		cba.pList=newList;
		cba.notifyDataSetChanged();
		
	}
	
	
	
	//������������
		public void findByChinese(){
			newList.clear();
			String co=inputSearch.getText().toString();
			System.out.println("��ǰ�������ַ����ǣ�"+co);
			
			for (int i = 0; i <app.pList.size(); i++) {
				if(app.pList.get(i).getPerisonName().indexOf(co)!=-1){
					System.out.println("��ǰƥ��������ǣ�"+app.pList.get(i).getPerisonName());
					newList.add(app.pList.get(i));		
				}	
			}
			cba.pList=newList;
			cba.notifyDataSetChanged();
			
		}
		
	
	
}
