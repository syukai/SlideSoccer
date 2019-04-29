package com.example.slidesoccer.domain.model.panel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.slidesoccer.domain.model.space.Space;
import com.example.slidesoccer.domain.model.space.Spaces;
import com.example.slidesoccer.domain.type.Height;
import com.example.slidesoccer.domain.type.Width;
import com.example.slidesoccer.domain.type.position.Position;
import com.example.slidesoccer.domain.type.position.X;
import com.example.slidesoccer.domain.type.position.Y;

public class GeneralPanels {
	List<Panel> panels;
	
	Panels<SmallPanel> smallPanels = new Panels<>();
	Panels<TallPanel> tallPanels = new Panels<>();
	Panels<WidePanel> widePanels = new Panels<>();
	
	public GeneralPanels() {
		panels = new ArrayList<>();
	}
	
	public GeneralPanels(List<Panel> list) {
		panels = list;
		
		for(Panel p:panels) {
			if(p instanceof SmallPanel) {
				smallPanels.add((SmallPanel)p);
			}else if(p instanceof TallPanel) {
				tallPanels.add((TallPanel)p);
			}else if(p instanceof WidePanel) {
				widePanels.add((WidePanel)p);
			}
		}
		
	}
	
	public void addAll(List<? extends Panel> adder) {
		for(Panel p:adder) {
			add(p);
		}
	}
	public void add(Panel adder) {
		panels.add(adder);
		subAdd(adder);
	}
	private void subAdd(Panel p) {
		if(p instanceof SmallPanel) {
			smallPanels.add((SmallPanel)p);
		}else if(p instanceof TallPanel) {
			tallPanels.add((TallPanel)p);
		}else if(p instanceof WidePanel) {
			widePanels.add((WidePanel)p);
		}
	}
	
	int size() {
		return panels.size();
	}
	
	public Spaces findSpaces(Height height, Width width) {
		List<Position> positions = new ArrayList<>();
		
		for(int x=1; x<=width.getNum();x++) {
			for(int y=1; y<=height.getNum();y++) {
				positions.add(new Position(X.of(x), Y.of(y)));
			}
		}
		
		panels.forEach(p ->positions.removeAll(p.positions()));
		
		return new Spaces(positions.stream()
				.map(p -> new Space(p))
				.collect(Collectors.toList()));
	}
	
	public boolean equals(GeneralPanels target) {
//		GeneralPanels target = (GeneralPanels)object;
		if((size()!=target.size())) return false;
		
		if(!smallPanels.isSameList(target.smallPanels)) return false;
		
		if(!tallPanels.isSameList(target.tallPanels)) return false;
		
		if(!widePanels.isSameList(target.widePanels)) return false;
		
		for(Panel p:target.panels) {
			if(!panels.contains(p)) return false;
		}
		
		return true;
	}
}