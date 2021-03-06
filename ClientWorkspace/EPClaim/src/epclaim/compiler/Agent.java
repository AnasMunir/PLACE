package epclaim.compiler;

import epclaim.utils.CommonStringUtils;

public class Agent {
	private String agentName;
	private KnowledgeCollection knowledgeCollection;
	private KnowledgeCollection goalCollection;
	public ActionCollection getActionCollection() {
		return actionCollection;
	}

	public ActivityCollection getActivityCollection() {
		return activityCollection;
	}

	private ActionCollection actionCollection;
	private ActivityCollection activityCollection;
	public KnowledgeCollection getKnowledgeCollection() {
		return knowledgeCollection;
	}

	public KnowledgeCollection getGoalCollection() {
		return goalCollection;
	}

	public void setGoalCollection(KnowledgeCollection goalCollection) {
		this.goalCollection = goalCollection;
	}

	public void setKnowledgeCollection(KnowledgeCollection knowledgeCollection) {
		this.knowledgeCollection = knowledgeCollection;
	}
	
	public void setActionCollection(ActionCollection actionCollection) {
		this.actionCollection = actionCollection;
	}
	public void setActivityCollection(ActivityCollection activityCollection) {
		this.activityCollection = activityCollection;
	}
	public String getName() {
		return agentName;
	}

	public void setName(String name) {
		this.agentName = name;
	}

	public Agent(String agentName) {
		super();
		this.agentName = agentName;
	}

	@Override
	public String toString() {
		String str=CommonStringUtils.CurlyBraceOpen(true);
		//str+= "knowledge=" + knowledgeCollection;
		//str+="actions="+actionCollection;
		str+="activities="+activityCollection;
		str+=CommonStringUtils.CurlyBraceClose(true);
		return str;
		//return "Agent [agentName=" + agentName+"\n Knowledge =" + knowledgeCollection+"\nActions = " + actionCollection;
	}
	
}
