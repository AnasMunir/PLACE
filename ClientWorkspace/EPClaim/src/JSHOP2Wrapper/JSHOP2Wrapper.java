package JSHOP2Wrapper;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import antlr.RecognitionException;
import antlr.TokenStreamException;
import JSHOP2.InternalDomain;
import JSHOP2.Plan;

public class JSHOP2Wrapper {
	private String classPathJSHOP=null;
	
	public JSHOP2Wrapper() {
		super();
		this.classPathJSHOP = "F:\\EPClaim\\ClientWorkspace\\EPClaim\\bin";
		
	}
	public Plan getPlans(String domainFile,String problemFile,String folder){
		
		
		String domainFileName = folder+domainFile;
		String problemFileName = folder+problemFile;
		File file = new File(domainFileName);
		System.out.println(file.getPath());
		try {
			InternalDomain internalDomain = new InternalDomain(new File(domainFileName), -1);			
			internalDomain.getParser().domain();
			InternalDomain internalDomainProblem = new InternalDomain(new File(problemFileName), 1);			
			internalDomainProblem.getParser().command();
			JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
			File problemJavaFile = new File(folder+"problem.java");
			System.out.println(problemJavaFile.getPath());
			System.out.println("Compiler Status: "+compiler);
			String classPath = folder+";"+this.classPathJSHOP;
			System.out.println("Class Path: "+ classPath);
			compiler.run(null, null, null, (new File(folder+domainFile+".java")).getPath());
			compiler.run(null, null, null, "-classpath",classPath,"-sourcepath",folder,problemJavaFile.getPath());
			
			System.out.println("Done Compilation!");
			
		} catch (IOException | RecognitionException | TokenStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
		
			ArrayList<String> classFiles = getAllClassFiles(folder);
			ClassLoader classLoader = ProblemClassLoader.class.getClassLoader();
			ProblemClassLoader problemClassLoader = new ProblemClassLoader(classLoader);
			try {
			Class<?> problem = null;
			ArrayList<Class<?>> allClasses = new ArrayList<Class<?>>();
			for(String className:classFiles){
				Class<?> dummy = problemClassLoader.loadClass(className,folder);
				if(className.equals("problem"))
					problem = dummy;
				allClasses.add(dummy);
				
			}
			
			//Class<?> problem = problemClassLoader.loadClass("problem",folder);
			Method method = problem.getMethod("getPlans", null);
			LinkedList<Plan> plans =(LinkedList<Plan>) method.invoke(null, null);
			//System.out.println("aClass.getName() = " + problem.getName());
			//System.out.println("Plans: "+ plans);
			return plans.get(0);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	private ArrayList<String> getAllClassFiles(String folder) {
		ArrayList<String> classFiles = new ArrayList<String>();
		File folderToLoad = new File(folder);
		for(File f: folderToLoad.listFiles()){
			String classFileName = f.getName();
			if(classFileName.endsWith(".class"))
			{
				String classFileNameWithoutDotClass = classFileName.substring(0,classFileName.length()-6);
				classFiles.add(classFileNameWithoutDotClass);
				//System.out.println(classFileNameWithoutDotClass);
			}
		}
		return classFiles;
	}
	public static void main(String[] args) {
		String domainFile = "blocks";
		String folder = "F:\\epclaimcodes\\shop2\\"+domainFile+"\\";
		JSHOP2Wrapper jshopWrapper = new JSHOP2Wrapper();
		Plan plan = jshopWrapper.getPlans(domainFile, "problem", folder);
		System.out.println(plan);
		//jshopWrapper.cleanFolder(domainFile, "problem", folder);
	}
	public void cleanFolder(String domainFile,String problemFile,String folder){
		File fileToDelete = new File(folder+domainFile+".java");
		fileToDelete.delete();
		fileToDelete = new File(folder+domainFile+".txt");
		fileToDelete.delete();
		fileToDelete = new File(folder+problemFile+".java");
		fileToDelete.delete();
		ArrayList<String> classFiles = getAllClassFiles(folder);
		for(String classFileName:classFiles){
			fileToDelete = new File(folder+classFileName+".class");
			//System.out.println(fileToDelete.getAbsolutePath());
			fileToDelete.delete();
		}
	}
}
