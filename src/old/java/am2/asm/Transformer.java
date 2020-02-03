package am2.asm;

import am2.*;
import com.google.common.collect.*;
import net.minecraft.launchwrapper.*;
import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;

import java.util.*;

import static org.objectweb.asm.Opcodes.*;

public class Transformer implements IClassTransformer {

	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass) {
		if (transformedName.equalsIgnoreCase("net.minecraft.client.renderer.block.model.BlockPart$Deserializer")) {
			ClassReader cr = new ClassReader(basicClass);
			ClassNode cn = new ClassNode();
			cr.accept(cn, 0);
			InsnList newInsn = new InsnList();
			newInsn.add(new VarInsnNode(ALOAD, 1));
			newInsn.add(new LdcInsnNode("angle"));
			newInsn.add(new MethodInsnNode(INVOKESTATIC, "net/minecraft/util/JsonUtils", Preloader.isDevEnvironment ? "getFloat" : "func_151217_k", "(Lcom/google/gson/JsonObject;Ljava/lang/String;)F", false));
			newInsn.add(new InsnNode(FRETURN));
			for (MethodNode mn : cn.methods) {
				if ((mn.name.equals("parseAngle") || mn.name.equals("func_178255_b")) && mn.desc.equals("(Lcom/google/gson/JsonObject;)F")) {
					LogHelper.info("Core: Removing Model Rotation Limit...");
					mn.instructions = newInsn;
				}
			}
			ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
			cn.accept(cw);
			return cw.toByteArray();
		} else if (transformedName.equalsIgnoreCase("net.minecraft.client.renderer.EntityRenderer"))
			return patchEntityRenderer(basicClass, !Preloader.isDevEnvironment);
		else if (transformedName.equalsIgnoreCase("net.minecraft.client.entity.EntityPlayerSP"))
			return alterEntityPlayerSP(basicClass, !Preloader.isDevEnvironment);
		else if (transformedName.equalsIgnoreCase("net.minecraft.entity.EntityLivingBase"))
			return alterEntityLivingBase(alterEntity(basicClass, !Preloader.isDevEnvironment), !Preloader.isDevEnvironment);
		else if (transformedName.equalsIgnoreCase("net.minecraft.entity.Entity"))
			return alterEntity(basicClass, !Preloader.isDevEnvironment);
		else if (transformedName.equalsIgnoreCase("net.minecraft.entity.player.EntityPlayer"))
			return alterEntityPlayer(basicClass, !Preloader.isDevEnvironment);
		else if (transformedName.equalsIgnoreCase("net.minecraftforge.client.model.obj.OBJModel$OBJBakedModel"))
			return alterObjBakedModel(basicClass, !Preloader.isDevEnvironment);
		else if (transformedName.equalsIgnoreCase("net.minecraft.potion.PotionEffect"))
			return alterPotionEffect(basicClass, !Preloader.isDevEnvironment);
		return basicClass;
	}
	
	private byte[] alterPotionEffect(byte[] bytes, boolean is_obfuscated) {
		ClassReader cr = new ClassReader(bytes);
		ClassNode cn = new ClassNode();
		cr.accept(cn, 0);
		LogHelper.info("Located OBJBakedModel");
		for (MethodNode mn : cn.methods) {
			if (mn.name.equals("readCustomPotionEffectFromNBT") || mn.name.equals("func_82722_b")) {
				Iterator<AbstractInsnNode> iter = mn.instructions.iterator();
				LogHelper.info("Core: Located target method " + mn.name + mn.desc);
				while (iter.hasNext()) {
					InsnList toAdd = new InsnList();
					toAdd.add(new LabelNode());
					toAdd.add(new VarInsnNode(ALOAD, 0));
					toAdd.add(new MethodInsnNode(INVOKESTATIC, "am2/api/event/PotionEvent$EventPotionLoaded", "post", "(Lnet/minecraft/potion/PotionEffect;Lnet/minecraft/nbt/NBTTagCompound;)Lnet/minecraft/potion/PotionEffect;", false));
					AbstractInsnNode ain = (AbstractInsnNode) iter.next();
					if (ain != null && ain.getOpcode() == ARETURN && ain.getPrevious().getOpcode() != ACONST_NULL) {
						mn.instructions.insertBefore(ain, toAdd);
					}
				}
			}
		}
		
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		cn.accept(cw);
	return cw.toByteArray();}

	private byte[] alterObjBakedModel(byte[] bytes, boolean is_obfuscated) {
		ClassReader cr = new ClassReader(bytes);
		ClassNode cn = new ClassNode();
		cr.accept(cn, 0);
		LogHelper.info("Located OBJBakedModel");
		for (MethodNode mn : cn.methods) {;
			if (mn.name.equals("buildQuads")) {
				Iterator<AbstractInsnNode> iter = mn.instructions.iterator();
				LogHelper.info("Core: Located target method " + mn.name + mn.desc);
				while (iter.hasNext()) {
					InsnList toAdd = new InsnList();
					toAdd.add(new LabelNode());
					toAdd.add(new VarInsnNode(ALOAD, 7));
					toAdd.add(new VarInsnNode(ALOAD, 6));
					toAdd.add(new MethodInsnNode(INVOKEVIRTUAL, "net/minecraftforge/client/model/obj/OBJModel$Face", "getMaterialName", "()Ljava/lang/String;", false));
					toAdd.add(new MethodInsnNode(INVOKESTATIC, "am2/api/event/OBJQuadEvent", "post", "(Ljava/lang/String;)I", false));
					toAdd.add(new MethodInsnNode(INVOKEVIRTUAL, "net/minecraftforge/client/model/pipeline/UnpackedBakedQuad$Builder", "setQuadTint", "(I)V", false));
					AbstractInsnNode ain = (AbstractInsnNode) iter.next();
					if (ain != null && ain instanceof MethodInsnNode && ain.getOpcode() == INVOKESPECIAL && ((MethodInsnNode)ain).owner.equalsIgnoreCase("net/minecraftforge/client/model/pipeline/UnpackedBakedQuad$Builder")) {
						MethodInsnNode min = (MethodInsnNode) ain;
						LogHelper.info("Located " + min.owner + " - " + min.name + min.desc);
						if (!iter.hasNext())
							break;
						ain = iter.next();
						if (ain != null && ain instanceof VarInsnNode && ain.getOpcode() == ASTORE && ((VarInsnNode)ain).var == 7) {
							mn.instructions.insert(ain, toAdd);
							LogHelper.info("Adding colors to OBJ Models");
							break;
						}
					}
				}
			}
		}
		
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		cn.accept(cw);
		return cw.toByteArray();
	}
	
	private byte[] alterEntity(byte[] bytes, boolean is_obfuscated) {
		ClassReader cr = new ClassReader(bytes);
		ClassNode cn = new ClassNode();
		cr.accept(cn, 0);
		for (MethodNode mn : cn.methods) {
			if (mn.name.equals("getLook") || (mn.name.equals("func_70676_i") && mn.desc.equals("(F)Lnet/minecraft/util/math/Vec3d"))) {
				LogHelper.info("Core: Located target method " + mn.name + mn.desc);
				Iterator<AbstractInsnNode> iter = mn.instructions.iterator();
				while (iter.hasNext()) {
					InsnList toAdd = new InsnList();
					toAdd.add(new VarInsnNode(ALOAD, 0));
					toAdd.add(new MethodInsnNode(INVOKESTATIC, "am2/utils/EntityUtils", "correctLook", "(Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/entity/Entity;)Lnet/minecraft/util/math/Vec3d;", false));
					AbstractInsnNode ain = (AbstractInsnNode) iter.next();
					if (ain != null && ain.getOpcode() == ARETURN) {
						LogHelper.info("Core: Located target ARETURN insn node");
						mn.instructions.insertBefore(ain, toAdd);
					}
				}
			}
		}
		
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		cn.accept(cw);
		return cw.toByteArray();
	}
	
	private byte[] alterEntityPlayer(byte[] bytes, boolean is_obfuscated) {
		ClassReader cr = new ClassReader(bytes);
		ClassNode cn = new ClassNode();
		cr.accept(cn, 0);
		for (MethodNode mn : cn.methods) {;
			if (mn.name.equals("getEyeHeight") || (mn.name.equals("func_70047_e") && mn.desc.equals("()F"))) {
				LogHelper.info("Core: Located target method " + mn.name + mn.desc);
				Iterator<AbstractInsnNode> iter = mn.instructions.iterator();
				while (iter.hasNext()) {
					AbstractInsnNode ain = (AbstractInsnNode) iter.next();
					if (ain != null && ain.getOpcode() == FRETURN) {
						InsnList toAdd = new InsnList();
						toAdd.add(new VarInsnNode(ALOAD, 0));
						toAdd.add(new MethodInsnNode(INVOKESTATIC, "am2/utils/EntityUtils", "correctEyePos", "(FLnet/minecraft/entity/Entity;)F", false));
						LogHelper.info("Core: Located target ARETURN insn node");
						mn.instructions.insertBefore(ain, toAdd);
					}
				}
			}
		}
		
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		cn.accept(cw);
		return cw.toByteArray();
	}
	
	private byte[] alterEntityLivingBase(byte[] bytes, boolean is_obfuscated) {
		ClassReader cr = new ClassReader(bytes);
		ClassNode cn = new ClassNode();
		cr.accept(cn, 0);
		for (MethodNode mn : cn.methods) {;
			if (mn.name.equals("c") || mn.name.equals("addPotionEffect")) {
				if (mn.desc.equals("(Lrl;)V") || mn.desc.equals("(Lnet/minecraft/potion/PotionEffect;)V")) {
					LogHelper.info("Patching addPotionEffect");
					String className = "net/minecraft/potion/PotionEffect;";
					InsnList list = new InsnList();
					list.add(new TypeInsnNode(NEW, "am2/api/event/PotionEvent$EventPotionAdded"));
					list.add(new InsnNode(DUP));
					list.add(new VarInsnNode(ALOAD, 1));
					list.add(new MethodInsnNode(INVOKESPECIAL, "am2/api/event/PotionEvent$EventPotionAdded", "<init>", "(L" + className + ")V", false));
					list.add(new VarInsnNode(ASTORE, 2));
					list.add(new LabelNode());
					list.add(new FieldInsnNode(GETSTATIC, "net/minecraftforge/common/MinecraftForge", "EVENT_BUS", "Lnet/minecraftforge/fml/common/eventhandler/EventBus;"));
					list.add(new VarInsnNode(ALOAD, 2));
					list.add(new MethodInsnNode(INVOKEVIRTUAL, "net/minecraftforge/fml/common/eventhandler/EventBus", "post", "(Lnet/minecraftforge/fml/common/eventhandler/Event;)Z", false));
					list.add(new InsnNode(POP));
					list.add(new LabelNode());
					list.add(new VarInsnNode(ALOAD, 2));
					list.add(new MethodInsnNode(INVOKEVIRTUAL, "am2/api/event/PotionEvent$EventPotionAdded", "getEffect", "()L" + className, false));
					list.add(new VarInsnNode(ASTORE, 1));
					ListIterator<AbstractInsnNode> insns = mn.instructions.iterator();
					while (insns.hasNext()) {
						AbstractInsnNode insn = insns.next();
						if (insn instanceof LabelNode) {
							mn.instructions.insertBefore(insn, list);	
							break;
						}
					}
				}
			}
		}
		
		{
			MethodNode method = new MethodNode();
			method.name = !is_obfuscated ? "moveRelative" : "func_70060_a";
			method.desc = "(FFF)V";
			method.access = ACC_PUBLIC;
			method.exceptions = Lists.newArrayList();
			LabelNode endNode = new LabelNode();
			method.instructions.add(new LabelNode());
			method.instructions.add(new VarInsnNode(FLOAD, 1));
			method.instructions.add(new VarInsnNode(FLOAD, 2));
			method.instructions.add(new VarInsnNode(FLOAD, 3));
			method.instructions.add(new VarInsnNode(ALOAD, 0));
			method.instructions.add(new MethodInsnNode(INVOKESTATIC, "am2/utils/EntityUtils", "correctMouvement", "(FFFLnet/minecraft/entity/Entity;)Z", false));
			method.instructions.add(new JumpInsnNode(IFNE, endNode));
			method.instructions.add(new LabelNode());
			method.instructions.add(new VarInsnNode(ALOAD, 0));
			method.instructions.add(new VarInsnNode(FLOAD, 1));
			method.instructions.add(new VarInsnNode(FLOAD, 2));
			method.instructions.add(new VarInsnNode(FLOAD, 3));
			method.instructions.add(new MethodInsnNode(INVOKESPECIAL, "net/minecraft/entity/Entity", !is_obfuscated ? "moveRelative" : "func_70060_a", "(FFF)V", false));
			method.instructions.add(endNode);
			method.instructions.add(new InsnNode(RETURN));
			method.visitMaxs(0, 0);
			cn.methods.add(method);
		}
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		cn.accept(cw);
		return cw.toByteArray();
	}
	
	
	private byte[] alterEntityPlayerSP(byte[] bytes, boolean is_obfuscated){
		ClassReader cr = new ClassReader(bytes);
		ClassNode cn = new ClassNode();
		cr.accept(cn, 0);

		// Minecraft r1.7.10: net.minecraft.client.entity.EntityPlayerSP.java = blk.class

		// EntityPlayerSP.onLivingUpdate() = blk/e
		// MCP mapping: blk/e ()V net/minecraft/client/entity/EntityPlayerSP/func_70636_d ()V
		ObfDeobfPair method1_name = new ObfDeobfPair();
		method1_name.setVal("onLivingUpdate", false);
		method1_name.setVal("func_70636_d", true);

		String method1_desc = "()V";

		// MovementInput.updatePlayerMoveState() = bli/a
		// note that we don't need the class name, it's referencing an internal variable
		ObfDeobfPair method1_searchinstruction = new ObfDeobfPair();
		method1_searchinstruction.setVal("updatePlayerMoveState", false);
		method1_searchinstruction.setVal("func_78898_a", true);

		String searchinstruction_desc = "()V";

		for (MethodNode mn : cn.methods){
			if (mn.name.equals(method1_name.getVal(is_obfuscated)) && mn.desc.equals(method1_desc)){ //onLivingUpdate
				AbstractInsnNode target = null;
				LogHelper.info("Core: Located target method " + mn.name + mn.desc);
				Iterator<AbstractInsnNode> instructions = mn.instructions.iterator();
				//look for the line:
				//this.movementInput.updatePlayerMoveState();
				while (instructions.hasNext()){
					AbstractInsnNode node = instructions.next();
					if (node instanceof VarInsnNode && ((VarInsnNode)node).getOpcode() == ALOAD){ //this.
						node = instructions.next();
						if (node instanceof FieldInsnNode && ((FieldInsnNode)node).getOpcode() == GETFIELD){ //movementInput.
							node = instructions.next();
							if (node instanceof MethodInsnNode){
								MethodInsnNode method = (MethodInsnNode)node;
								if (method.name.equals(method1_searchinstruction.getVal(is_obfuscated)) && method.desc.equals(searchinstruction_desc)){ //updatePlayerMoveState
									LogHelper.info("Core: Located target method insn node: " + method.name + method.desc);
									target = node;
									break;
								}
							}
						}
					}
				}

				if (target != null){
					MethodInsnNode callout = new MethodInsnNode(INVOKESTATIC, "am2/gui/AMGuiHelper", "overrideKeyboardInput", "()V", false);
					mn.instructions.insert(target, callout);
					LogHelper.info("Core: Success!  Inserted operations!");
					break;
				}
			}
		}

		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		cn.accept(cw);
		return cw.toByteArray();
	}
	
	private byte[] patchEntityRenderer(byte[] basicClass, boolean isObf) {
		ClassReader cr = new ClassReader(basicClass);
		ClassNode cn = new ClassNode();
		cr.accept(cn, 0);
		
		ObfDeobfPair method1_name = new ObfDeobfPair();
		method1_name.setVal("setupCameraTransform", false);
		method1_name.setVal("func_78479_a", true);
		
		ObfDeobfPair method2_name = new ObfDeobfPair();
		method2_name.setVal("updateCameraAndRender", false);
		method2_name.setVal("func_181560_a", true);
		
		String method2_desc = "(FJ)V";
		
		// search for this function call:
		// net.minecraft.profiler.Profiler.startSection = qi.a
		// MCP mapping: MD: qi/a (Ljava/lang/String;)V net/minecraft/profiler/Profiler/func_76320_a (Ljava/lang/String;)V

		ObfDeobfPair method2_searchinstruction_function = new ObfDeobfPair();
		method2_searchinstruction_function.setVal("startSection", false);
		method2_searchinstruction_function.setVal("func_76320_a", true);

		String method2_searchinstruction_desc = "(Ljava/lang/String;)V";

		// we will be inserting a call to am2.guis.AMGuiHelper.overrideMouseInput()
		// description (Lnet/minecraft/client/renderer/EntityRenderer;FZ)Z
		
		for (MethodNode mn : cn.methods){
			if (mn.name.equals(method1_name.getVal(isObf)) && mn.desc.equals("(FI)V")){ // setupCameraTransform
				AbstractInsnNode orientCameraNode = null;
				AbstractInsnNode gluPerspectiveNode = null;
				LogHelper.info("Core: Located target method " + mn.name + mn.desc);
				Iterator<AbstractInsnNode> instructions = mn.instructions.iterator();
				while (instructions.hasNext()){
					AbstractInsnNode node = instructions.next();
					if (node instanceof MethodInsnNode){
						MethodInsnNode method = (MethodInsnNode)node;
						if (orientCameraNode == null && (method.name.equals("orientCamera") || method.name.equals("func_78467_g")) && method.desc.equals("(F)V")){ //orientCamera
							LogHelper.info("Core: Located target method insn node: " + method.name + method.desc);
							orientCameraNode = node;
							continue;
						}else if (gluPerspectiveNode == null && method.name.equals("gluPerspective") && method.desc.equals("(FFFF)V")){
							LogHelper.info("Core: Located target method insn node: " + method.name + method.desc);
							gluPerspectiveNode = node;
							continue;
						}
					}

					if (orientCameraNode != null && gluPerspectiveNode != null){
						//found all nodes we're looking for
						break;
					}
				}
				if (orientCameraNode != null){
					VarInsnNode floatset = new VarInsnNode(FLOAD, 1);
					MethodInsnNode callout = new MethodInsnNode(INVOKESTATIC, "am2/gui/AMGuiHelper", "shiftView", "(F)V", false);
					mn.instructions.insert(orientCameraNode, callout);
					mn.instructions.insert(orientCameraNode, floatset);
					LogHelper.info("Core: Success!  Inserted callout function op (shift)!");
				}
				if (gluPerspectiveNode != null){
					VarInsnNode floatset = new VarInsnNode(FLOAD, 1);
					MethodInsnNode callout = new MethodInsnNode(INVOKESTATIC, "am2/gui/AMGuiHelper", "flipView", "(F)V", false);
					mn.instructions.insert(gluPerspectiveNode, callout);
					mn.instructions.insert(gluPerspectiveNode, floatset);
					LogHelper.info("Core: Success!  Inserted callout function op (flip)!");
				}

			}else if (mn.name.equals(method2_name.getVal(isObf)) && mn.desc.equals(method2_desc)){  //updateCameraAndRender
				AbstractInsnNode target = null;
				LogHelper.info("Core: Located target method " + mn.name + mn.desc);
				Iterator<AbstractInsnNode> instructions = mn.instructions.iterator();
				AbstractInsnNode node = null;
				boolean mouseFound = false;
				while (instructions.hasNext()){
					node = instructions.next();
					//look for the line:
					//this.mc.mcProfiler.startSection("mouse");
					if (!mouseFound){
						if (node instanceof LdcInsnNode){
							if (((LdcInsnNode)node).cst.equals("mouse")){
								mouseFound = true;
							}
						}
					}else{
						if (node instanceof MethodInsnNode){
							MethodInsnNode method = (MethodInsnNode)node;
							if (method.owner.equals("net/minecraft/profiler/Profiler") && method.name.equals(method2_searchinstruction_function.getVal(isObf)) && method.desc.equals(method2_searchinstruction_desc)){
								LogHelper.info("Core: Located target method insn node: " + method.owner + "." + method.name + ", " + method.desc);
								target = node;
								break;
							}
						}
					}
				}

				if (target != null){
					int iRegister = 4;

					VarInsnNode aLoad = new VarInsnNode(ALOAD, 0);
					VarInsnNode fLoad = new VarInsnNode(FLOAD, 1);
					VarInsnNode iLoad = new VarInsnNode(ILOAD, iRegister);
					MethodInsnNode callout = new MethodInsnNode(INVOKESTATIC, "am2/gui/AMGuiHelper", "overrideMouseInput", "(Lnet/minecraft/client/renderer/EntityRenderer;FZ)Z", false);
					VarInsnNode iStore = new VarInsnNode(ISTORE, iRegister);

					mn.instructions.insert(target, iStore);
					mn.instructions.insert(target, callout);
					mn.instructions.insert(target, iLoad);
					mn.instructions.insert(target, fLoad);
					mn.instructions.insert(target, aLoad);
					LogHelper.info("Core: Success!  Inserted opcodes!");
				}
			}
		}
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		cn.accept(cw);
		return cw.toByteArray();
	}
	
	public class ObfDeobfPair{
		private String deobf_val;
		private String obf_val;

		public ObfDeobfPair(){
			deobf_val = "";
			obf_val = "";
		}

		public void setVal(String value, boolean is_obfuscated){
			if (is_obfuscated){
				obf_val = value;
			} else{
				deobf_val = value;
			}
		}

		public String getVal(boolean is_obfuscated){
			if (is_obfuscated){
				return obf_val;
			}
			else{
				return deobf_val;
			}
		}
	}

}
