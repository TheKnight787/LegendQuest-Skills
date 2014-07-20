package me.sablednah.legendquest.skills;

import java.util.UUID;

import me.sablednah.legendquest.playercharacters.PC;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@SkillManifest(
	name = "Boost", type = SkillType.ACTIVE, author = "SableDnah", version = 1.0D, 
	description = "Adjust your speed for [duration]", 
	consumes = "", manaCost = 5, levelRequired = 0, skillPoints = 0, 
	buildup = 0, delay = 0, duration = 5000, cooldown = 10000, 
	dblvarnames = { "speed" }, dblvarvalues = { 0.0 }, 
	intvarnames = {	}, intvarvalues = { }, 
	strvarnames = { }, strvarvalues = { }
)
public class Boost extends Skill {

	public boolean onEnable() {
		return true;
	}

	public void onDisable() { /* nothing to do */ }

	public CommandResult onCommand(Player p) {
		if (!validSkillUser(p)) {
			return CommandResult.FAIL;
		}

		// load skill options
		SkillDataStore data = this.getPlayerSkillData(p);

		Double speed = ((Double) data.vars.get("speed"));
		
		p.setWalkSpeed((float)speed.doubleValue());
		
		Bukkit.getServer().getScheduler().runTaskLater(lq, new ReSpeed(p.getUniqueId()), (long)(data.duration/50));
		
//		boolean test = Mechanics.opposedTest(getPC(p), Difficulty.TOUGH, Attribute.DEX, getPC(target), Difficulty.EASY, Attribute.WIS);

		return CommandResult.SUCCESS;
	}

	public class ReSpeed implements Runnable {
		UUID uuid;
		public ReSpeed(UUID u) {
			uuid=u;
		}
		public void run() {
			PC pc = getPC(uuid);
			if (pc!=null) {
				lq.getServer().getPlayer(uuid).setWalkSpeed(pc.race.baseSpeed);
			}
		}
	}
}
