package com.vplay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import vplay_engine.DebugClass;
import vplay_engine.VPlayApplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

//��������� ���� ���� ��������
public class WordmakerStartActivity extends Activity
{

	/*****	�������� ����	*****/
	
	//����� ��� �������� ������ �������� � ��������������� ������
	HashMap<String, String> spinner_map = new HashMap<String, String>();	
	
	/*****	����������� ������	*****/

	//����� ����� � ����
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wordmaker_start);
		
		//������������� ���������
		init_spinners();
	}	
	
	//������������� ��������
	private void init_spinners()
	{
		//������ ��� ��������
		List<String> spinner_list = new ArrayList<String>();
		
		//��������� ��������� �� ����������
		VPlayApplication app = VPlayApplication.get_instance();
		
		//��������� ���������
		Context context = app.getApplicationContext();
		
		//��������� ������
		AssetManager asset_manager = context.getAssets();
		
		//����� ��� ��������� ������ ������
		InputStream is = null;
		
		//������ ������ �����
		BufferedReader reader = null;
		
		//������ ��� ������ ������ ���������
		String line;
		
		//������� ���� � �������� ������
		try
		{
			is = asset_manager.open("wordmaker_game/game_themes.txt");
		}
		catch(IOException e)
		{
			DebugClass.message("����������� ���� � ������ ��� ����");
			return;
		}
		
		//������ ������� �� �����
		try
		{
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		}
		catch(UnsupportedEncodingException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try
		{
			while((line = reader.readLine()) != null)
			{
				//�������� ����. ����
				String theme_name = line;
				String theme_filename = reader.readLine();
				
				//�������� � ���
				spinner_map.put(theme_name, theme_filename);
				
				//�������� � ������
				spinner_list.add(theme_name);
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		//������� ���� � �������������� ����������� ��� �������
		try
		{
			is.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		//������� ��� �������
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinner_list);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		//�������� ������ � GUI ����������
		Spinner spinner = (Spinner) findViewById(R.id.wordmaker_start_game_theme_spinner);
		
		//���������� �� ���� �������
		spinner.setAdapter(adapter);
		
		//������� ������ ������� � ������
		spinner.setSelection(0);
	}
	
	
	
	/*****	������ ������� ������	*****/
	
	//������� �� ������ - ������ ����
	public void on_click_wordmaker_start_start_game_button(View v)
	{
		//�������� ������ � GUI ����������
		Spinner spinner = (Spinner) findViewById(R.id.wordmaker_start_game_theme_spinner);		
				
		//�������� ������� �������� ����
		String theme_name = (String)spinner.getSelectedItem();
		
		Intent i = new Intent(getApplicationContext(), WordmakerActivity.class);
		i.putExtra("game_theme", spinner_map.get(theme_name));
		startActivity(i);
	}
}
