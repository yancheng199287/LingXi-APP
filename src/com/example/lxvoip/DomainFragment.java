package com.example.lxvoip;

import com.example.lxvoip.domain.fragment.About;
import com.example.lxvoip.domain.fragment.HelpCenter;
import com.example.lxvoip.domain.fragment.Http;
import com.example.lxvoip.domain.fragment.PersonCenter;
import com.example.lxvoip.domain.fragment.SaleActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Toast;

public class DomainFragment extends Fragment implements OnClickListener{
	
	View personCenter;
	View helpCenter;
	View saleActivity;
	View shareFriends;
	View http;
	View requestion;
	View softupdate;
	View aboutus;
	View vv;
	//嵌套
	FragmentManager fgm;
	
	Fragment personcenter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 vv=inflater.inflate(R.layout.domain, container, false);
		 init();
		 
		return vv;
	}
	
	
	
	public void init(){
		 personCenter=vv.findViewById(R.id.personCenter);
		 personCenter.setOnClickListener(this);
		 helpCenter=vv.findViewById(R.id.helpCenter);
		 helpCenter.setOnClickListener(this);
		 saleActivity=vv.findViewById(R.id.saleActivity);
		 saleActivity.setOnClickListener(this);
		 shareFriends=vv.findViewById(R.id.shareFriends);
		 shareFriends.setOnClickListener(this);
		 http=vv.findViewById(R.id.http);
		 http.setOnClickListener(this);
		 requestion=vv.findViewById(R.id.requestion);
		 requestion.setOnClickListener(this);
		 softupdate=vv.findViewById(R.id.softupdate);
		 softupdate.setOnClickListener(this);
		 
		 aboutus=vv.findViewById(R.id.aboutus);
		 aboutus.setOnClickListener(this);
		 
		 fgm=this.getChildFragmentManager();

	}



	@Override
	public void onClick(View v) {
	
		switch (v.getId()) {
		case R.id.personCenter:
			setChoiceActivity(PersonCenter.class);
			break;
	case R.id.helpCenter:
		setChoiceActivity(HelpCenter.class);
		
			break;
	case R.id.saleActivity:
		setChoiceActivity(SaleActivity.class);
		break;
	case R.id.http:
		setChoiceActivity(Http.class);
		break;
	case R.id.requestion:
	//	setChoiceActivity(Http.class);
		break;
	case R.id.softupdate:
		Toast.makeText(getActivity(), "软件无需升级，已经是最新版本！", 3000).show();
		break;
	case R.id.aboutus:
		setChoiceActivity(About.class);
		
		}
		
	}
	
	
	
	
	public void setChoiceActivity(Class other){
	Intent intent=new Intent(getActivity(),other);
		this.startActivity(intent);
		
	}
	
	
	
	
}
