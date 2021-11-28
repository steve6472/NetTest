package steve6472.netest.client.gui;

import steve6472.sge.gfx.Render;
import steve6472.sge.gui.Component;
import steve6472.sge.gui.components.Button;
import steve6472.sge.gui.components.Slider;
import steve6472.sge.main.MainApp;
import steve6472.sge.main.util.ColorUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/28/2021
 * Project: NetTest
 *
 ***********************/
public class SliderColorSelectComp extends Component
{
	protected Slider redSlider, greenSlider, blueSlider;
	private int redMin, redMax, greenMin, greenMax, blueMin, blueMax;

	private final int startingColor;

	protected BiConsumer<Button, SliderColorSelectComp> okEvent;
	private Button okButton;
	private List<Consumer<SliderColorSelectComp>> changeEvents = new ArrayList<>();

	public SliderColorSelectComp(int startingColor)
	{
		this.startingColor = startingColor;
	}

	@Override
	public void init(MainApp main)
	{
		setSize(276 + 60, 138);

		redSlider = new Slider()
		{
			@Override
			public void renderBar()
			{
				super.renderBar();
				Render.fillRect(x + 4, y + 4, width - 8, height - 8, redMin, redMin, redMax, redMax);
			}
		};
		redSlider.setSize(256, 16);
		redSlider.setRelativeLocation(10, 30);
		redSlider.setButtonSize(16, 30);
		redSlider.setMaxValue(255);
		redSlider.setValue(ColorUtil.getRed(startingColor));
		redSlider.addChangeEvent(c -> changeEvents.forEach(d -> d.accept(this)));
		addComponent(redSlider);

		greenSlider = new Slider()
		{
			@Override
			public void renderBar()
			{
				super.renderBar();
				Render.fillRect(x + 4, y + 4, width - 8, height - 8, greenMin, greenMin, greenMax, greenMax);
			}
		};
		greenSlider.setSize(256, 16);
		greenSlider.setRelativeLocation(10, 70);
		greenSlider.setButtonSize(16, 30);
		greenSlider.setMaxValue(255);
		greenSlider.setValue(ColorUtil.getGreen(startingColor));
		greenSlider.addChangeEvent(c -> changeEvents.forEach(d -> d.accept(this)));
		addComponent(greenSlider);

		blueSlider = new Slider()
		{
			@Override
			public void renderBar()
			{
				super.renderBar();
				Render.fillRect(x + 4, y + 4, width - 8, height - 8, blueMin, blueMin, blueMax, blueMax);
			}
		};
		blueSlider.setSize(256, 16);
		blueSlider.setRelativeLocation(10, 110);
		blueSlider.setButtonSize(16, 30);
		blueSlider.setMaxValue(255);
		blueSlider.setValue(ColorUtil.getBlue(startingColor));
		blueSlider.addChangeEvent(c -> changeEvents.forEach(d -> d.accept(this)));
		addComponent(blueSlider);

		okButton = new Button("Ok");
		okButton.setRelativeLocation(276, 64);
		okButton.setSize(50, 30);
		okButton.addClickEvent(c ->
		{
			if (okEvent != null)
				okEvent.accept(c, this);
		});
		addComponent(okButton);
	}

	@Override
	public void setLocation(int x, int y)
	{
		super.setLocation(x, y);
		redSlider.setRelativeLocation(10, 30);
		greenSlider.setRelativeLocation(10, 70);
		blueSlider.setRelativeLocation(10, 110);
		okButton.setRelativeLocation(276, 64);
	}

	@Override
	public void tick()
	{
		redMin = ColorUtil.getColor(0, greenSlider.getIValue(), blueSlider.getIValue());
		greenMin = ColorUtil.getColor(redSlider.getIValue(), 0, blueSlider.getIValue());
		blueMin = ColorUtil.getColor(redSlider.getIValue(), greenSlider.getIValue(), 0);

		redMax = ColorUtil.getColor(255, greenSlider.getIValue(), blueSlider.getIValue());
		greenMax = ColorUtil.getColor(redSlider.getIValue(), 255, blueSlider.getIValue());
		blueMax = ColorUtil.getColor(redSlider.getIValue(), greenSlider.getIValue(), 255);
	}

	public int getColor()
	{
		return ColorUtil.getColor(redSlider.getIValue(), greenSlider.getIValue(), blueSlider.getIValue());
	}

	public int getRed()
	{
		return redSlider.getIValue();
	}

	public int getGreen()
	{
		return greenSlider.getIValue();
	}

	public int getBlue()
	{
		return blueSlider.getIValue();
	}

	@Override
	public void render()
	{

	}
}
