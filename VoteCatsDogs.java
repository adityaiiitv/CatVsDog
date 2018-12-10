import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.InputStream;

class Vote 
{
	private String retain;	// Retain
	private String eliminate;	// Eliminate
	private List<Vote> conflictVotes;	// Conflicting votes
	private boolean exploredVotes;	// Seen Votes
	private Vote next;

	public Vote(String retain, String eliminate) 
	{
		super();
		this.exploredVotes = false;
		this.conflictVotes = new ArrayList<Vote>();
		this.next = null;
		this.retain = retain;
		this.eliminate = eliminate;
	}

	public Vote getLink() 
	{
		return next;
	}

	public void setLink(Vote next) 
	{
		this.next = next;
	}

	public String getRetain() 
	{
		return retain;
	}

	public void setRetain(String retain) 
	{
		this.retain = retain;
	}
	
	public String getEliminate() 
	{
		return eliminate;
	}
	
	public void setEliminate(String eliminate) 
	{
		this.eliminate = eliminate;
	}
	
	public List<Vote> getIncompatible() 
	{
		return conflictVotes;
	}
	
	public void setIncompatible(List<Vote> notCompatible) 
	{
		this.conflictVotes = notCompatible;
	}
	
	public boolean isAlreadyExplored() 
	{
		return exploredVotes;
	}
	
	public void setAlreadyExplored(boolean alreadyExplored) 
	{
		this.exploredVotes = alreadyExplored;
	}

	public void addIncompatibleRule(Vote aux)
	{
		if(!this.conflictVotes.contains(aux))
		{	
			this.conflictVotes.add(aux);
		}
	}
	public String toString()
	{
		return this.retain + " - " + this.eliminate;
	}
}

public class VoteCatsDogs 
{
	public static void main(String[] args) 
	{
		System.out.println(solver(System.in));
	}
	
	public static String solver(InputStream inputStr)
	{
		String ans = "";
		Scanner scan = new Scanner(inputStr);
		int scenarios = scan.nextInt();

		for(int i=0;i<scenarios;i++)
		{
			// Reading number of cats, dogs and voters
			int c = scan.nextInt();
			int d = scan.nextInt();
			int v = scan.nextInt();

			// Arrays to store the votes depending on the preference
			List<Vote> catsVoters = new ArrayList<Vote>();
			List<Vote> dogsVoters = new ArrayList<Vote>();

			// Arrays to store the votes depending on the preference
			for(int j=0;j<v;j++)
			{
				String keep = scan.next();
				String eliminate = scan.next();

				if(keep.startsWith("C"))
				{
					catsVoters.add(new Vote(keep, eliminate));
				}
				else if(keep.startsWith("D"))
				{
					dogsVoters.add(new Vote(keep, eliminate));
				}
			}

			// Storing for every Vote other incompatible Votes
			for(int j=0;j<catsVoters.size();j++)
			{
				for(int k=0;k<dogsVoters.size();k++)
				{
					if(catsVoters.get(j).getRetain().compareTo(dogsVoters.get(k).getEliminate())==0
							|| catsVoters.get(j).getEliminate().compareTo(dogsVoters.get(k).getRetain())==0)
					{
						// Store the incompatibilities only in the cats lover votes
						catsVoters.get(j).addIncompatibleRule(dogsVoters.get(k));
					}
				}
			}

			// Retrieve maximum number of incompatibilities
			int conflicts = solve(catsVoters, dogsVoters);

			// Maximum number of voters satisfied would be total_number_voters - max_incompatibilities
			ans = ans + (v-conflicts) + "\n";
		}
		
		return ans;
	}

	// Solver
	public static int solve(List<Vote> catLovers, List<Vote> dogLovers)
	{
		int sum = 0;
		// Iterate through the cat votes
		for(int i=0;i<catLovers.size();i++)
		{
			Vote catNode = catLovers.get(i);

			// Set all the alreadyExplored variables to false
			for(int j=0;j<dogLovers.size();j++)
			{	
				dogLovers.get(j).setAlreadyExplored(false);
			}

			// If this vote can be assigned to another incompatible vote, increment sum
			if(assign(catNode))
			{ 
				sum++;
			}
		}
		return sum;
	}

	// Check to assign votes
	private static boolean assign(Vote catNode) 
	{
		for(int j=0;j<catNode.getIncompatible().size();j++)
		{
			Vote dogNode = catNode.getIncompatible().get(j);
			if(!dogNode.isAlreadyExplored())
			{
				dogNode.setAlreadyExplored(true);
				if (dogNode.getLink()==null || assign(dogNode.getLink()))
				{
					catNode.setLink(dogNode);
					dogNode.setLink(catNode);
					return true;
				}
			}
		}
		return false;
	}
}
