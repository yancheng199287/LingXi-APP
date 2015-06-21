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
			Toast.makeText(getActivity(), "你点击了："+num, 3000).show();	
				new DialerFragment().call(num);
				
			}
		});
		 
		 
		 
		 
		return vv;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
 public void init(){
	 View header =getActivity(). getLayoutInflater().inflate(R.layout.contact_header_my, null);

	 inputSearch=(EditText) vv.findViewById(R.id.inputSearch);
	 //为检索编辑框添加内容改变事件
	 searchContact(inputSearch);
	 
	 
	 contactListView=(ListView)vv.findViewById(R.id.contactListView);
	 
	 contactListView.addHeaderView(header);
	 
	alpha=(QuickAlphabeticBar)vv.findViewById(R.id.fast_scroller);
	
	app = (MyApplication) getActivity().getApplicationContext(); 
	filldata();
 }
 
 
 public void filldata(){
		
		
		
		if(app.pList.size()>0){
		
			System.out.println("取到联系人啦！"+app.pList.size());
			
			 cba=new ContactAdapter(getActivity(), app.pList, alpha);
				
			contactListView.setAdapter(cba);
			
			//给右边字母侧边栏设置属性，给一个上下文做吃初始化
		
			alpha.init(vv);
			
			//传一个联系人listview对象
			alpha.setListView(contactListView);
			
			//设置高度
		//	alpha.setHight(alpha.getHeight());
			//设置显示
			alpha.setVisibility(View.VISIBLE);
					
		}else{
	
			System.out.println("meiyou联系人啦！");
		}

		
}
	
	
	
	
 
	public void searchContact(EditText et){
		
		et.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			String str=s.toString();
			
			//判断当前输入的数字 还是字母还是中文。在这里我们都支持搜索
			//如果是数字就是匹配电话号码，如果是字母那就是pinyin，如果是简拼数字FormattedNumber暂时不做；
			int key=IsStrType.tell(str);
			switch (key) {
			case 0:
				break;
			case 1:
				System.out.println("你输入的是数字，匹配电话号码");
				findByNumber();
				break;
			case 2:
				System.out.println("你输入的是字母，匹配pinyin");
				 findByPinyin();
				break;
			case 3:
				System.out.println("你输入的是中文，匹配人名");
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
	
	
	
	//匹配数字，我们找到电话号码检索
	ArrayList<ContactPerson> newList=new ArrayList<ContactPerson>();
	public void findByNumber(){
		newList.clear();
		String co=inputSearch.getText().toString();
		System.out.println("当前检索的字符串是："+co);
		
		for (int i = 0; i <app.pList.size(); i++) {
			
			if(app.pList.get(i).getPhoneNumber().indexOf(co)!=-1 || (app.pList.get(i).getFormattedNumber().indexOf(co)!=-1)){
				
				System.out.println("当前匹配的电话号码是："+app.pList.get(i).getPhoneNumber());
				newList.add(app.pList.get(i));		
			}	
		}
		cba.pList=newList;
		cba.notifyDataSetChanged();
		
	}
	
	//根据拼音查找
	public void findByPinyin(){
		newList.clear();
		String co=inputSearch.getText().toString();
		System.out.println("当前检索的字符串是："+co);
		
		for (int i = 0; i <app.pList.size(); i++) {
			
			if(app.pList.get(i).getPinyin().toLowerCase().indexOf(co)!=-1|| app.pList.get(i).getSortkey().toLowerCase().indexOf(co)!=-1){
				System.out.println("当前匹配的拼音是："+app.pList.get(i).getPinyin());
				newList.add(app.pList.get(i));		
			}	
		}
		cba.pList=newList;
		cba.notifyDataSetChanged();
		
	}
	
	
	
	//根据姓名查找
		public void findByChinese(){
			newList.clear();
			String co=inputSearch.getText().toString();
			System.out.println("当前检索的字符串是："+co);
			
			for (int i = 0; i <app.pList.size(); i++) {
				if(app.pList.get(i).getPerisonName().indexOf(co)!=-1){
					System.out.println("当前匹配的姓名是："+app.pList.get(i).getPerisonName());
					newList.add(app.pList.get(i));		
				}	
			}
			cba.pList=newList;
			cba.notifyDataSetChanged();
			
		}
		
	
	
}
