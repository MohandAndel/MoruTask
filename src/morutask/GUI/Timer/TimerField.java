/*
 * TaskUnifier
 * Copyright (c) 2011, Benjamin Leclerc
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of TaskUnifier or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package morutask.GUI.Timer;

import com.leclercb.commons.api.utils.CheckUtils;
import java.awt.BorderLayout;
import java.text.ParseException;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import morutask.models.Timer;

/**
 *
 * @author mohand
 */
public class TimerField extends JPanel {
	
	private boolean showButton;
	private Timer timer;
	
	private JSpinner timeSpinner;
	//private JToggleButton button;
	
        public TimerField()
        {
            this(true);
        }
        
	public TimerField(boolean showButton) {
		this(showButton, new Timer());
	}
	
	public TimerField(boolean showButton, Timer timer) {
		this.showButton = showButton;
		this.initialize();
		this.setTimer(timer);
	}
	
	public void commitEdit() {
		try {
			this.timeSpinner.commitEdit();
		} catch (ParseException e) {
			
		}
	}
	
	public Timer getTimer() {
		return this.timer;
	}
	
	public void setTimer(Timer timer) {
		CheckUtils.isNotNull(timer);
		this.timer = timer;
		
		this.timeSpinner.setValue((int) (this.timer.getTimerValue() / 60));
		//this.button.setSelected(this.timer.isStarted());
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		this.timeSpinner.setEnabled(enabled);
		//this.button.setEnabled(enabled);
	}
	
	private void initialize() {
		this.timeSpinner = new JSpinner();
		this.timeSpinner.setModel(new SpinnerTimeModel());
		this.timeSpinner.setEditor(new SpinnerTimeEditor(this.timeSpinner));
		
		//this.button = new JToggleButton("AA");
		//this.button.setIcon(new ImageIcon("images/pause.png"));//ImageUtils.getResourceImage("pause.png", 16, 16));
		//this.button.setSelectedIcon(new ImageIcon("images/play.png"));//ImageUtils.getResourceImage("play.png",16,16));
		
		//this.button.setBorderPainted(false);
		//this.button.setContentAreaFilled(false);
		
		this.setLayout(new BorderLayout());
		this.add(this.timeSpinner, BorderLayout.CENTER);
		
		//if (this.showButton)
			//this.add(this.button, BorderLayout.WEST);
		
		this.timeSpinner.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent evt) {
				TimerField.this.timer.setValue(((Number) TimerField.this.timeSpinner.getValue()).longValue() * 60);
			}
			
		});
		
//		this.button.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent evt) {
//				if (TimerField.this.button.isSelected())
//					TimerField.this.timer.start();
//				else
//					TimerField.this.timer.stop();
//			}
//			
//		});
	}
	
	public void addChangeListener(ChangeListener listener) {
		this.timeSpinner.addChangeListener(listener);
	}
	
	public void removeChangeListener(ChangeListener listener) {
		this.timeSpinner.removeChangeListener(listener);
	}
	
}

