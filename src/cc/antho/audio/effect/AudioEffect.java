package cc.antho.audio.effect;

import static org.lwjgl.openal.EXTEfx.*;

import java.util.ArrayList;
import java.util.List;

import cc.antho.common.Destroyable;
import cc.antho.common.log.Logger;
import lombok.Getter;

public abstract class AudioEffect implements Destroyable {

	@Getter protected int handle;
	@Getter private final int type;

	@Getter private List<AudioEffectSlot> slots = new ArrayList<>();

	public AudioEffect(int handle, int type) {

		Logger.debug("Creating audio effect");

		this.handle = handle;
		this.type = type;
		alEffecti(handle, AL_EFFECT_TYPE, type);

		load();

	}

	public AudioEffect(int type) {

		this(alGenEffects(), type);

	}

	public void load() {

		for (int i = 0; i < slots.size(); i++)
			slots.get(i).setEffect(this);

	}

	public void destroy() {

		Logger.debug("Destroying audio effect");

		for (int i = 0; i < slots.size(); i++)
			slots.get(i).setEffect(null);

		slots.clear();

		alDeleteEffects(handle);
		handle = 0;

	}

}
