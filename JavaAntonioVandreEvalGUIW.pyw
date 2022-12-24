# Proprietário: Antonio Vandré Pedrosa Furtunato Gomes (bit.ly/antoniovandre_legadoontologico).

# Projeto Mathematical Ramblings (bit.ly/mathematicalramblings_github).

# Arquivo Python para "AntonioVandreEval", versão de distribuição.

# Última atualização: 24-12-2022.

import tkinter as tk

from tkinter import *

import os

import sys

import subprocess

import ctypes

# Diretório de instalação customizável.

os.chdir ("C:\JavaAntonioVandreEvalGUIW")

user32 = ctypes.windll.user32

try:
	import pyi_splash
	pyi_splash.update_text('UI carregada...')
	pyi_splash.close()
except:
	pass

splash = Tk()
splash.title("Ave à deusa Matemática...")
splash.geometry("400x400+" + str(int(user32.GetSystemMetrics(0) / 2 - 200)) + "+" + str(int(user32.GetSystemMetrics(1) / 2 - 200)))
splash.after(3000, splash.destroy)
bg = PhotoImage(file = "JavaAntonioVandreEvalGUI - Logo - 400p.png")
lab = Label(splash, image = bg)
lab.pack()

splash.mainloop()

window = Tk ()

window.geometry('410x150')

window.resizable (False, False)

iconimg = PhotoImage(file="Mathematical Ramblings - Icon - 100p.png")

window.iconphoto(False, iconimg)

bgimage = PhotoImage (file="antoniovandre_eval_gui_bgimage.png")

bglabel = Label (window, image=bgimage)

bglabel.place (x=0, y=0, relwidth=1, relheight=1)

window.title ("JavaAntonioVandreEvalGUIW 24-12-2022")

lbl_entre = Label (window, text="Expressão:")

lbl_entre.place (anchor='w', rely=.1)

txt = Entry (window, width=67)

txt.place (anchor='w', rely=.3)

lbl_resultado = Label (window, text="Resultado:")

lbl_resultado.place (anchor='w', rely=.7)

lbl_saida = Label (window, text="Entre com a expressão e clique em calcular.")

lbl_saida.place (anchor='w', rely=.9)

def clicked ():

	"""
	os.system('java -jar JavaAntonioVandreEval.jar ' + txt.get().replace("^", "^^") + ' > avegt')

	with open('avegt') as f:
		lines = f.readlines()

	processo_return = lines[0]

	lbl_saida.configure (text=processo_return.rstrip("\n"))

	os.system('del avegt')

	"""
	processo_return = subprocess.check_output(['java', '-jar', 'JavaAntonioVandreEval.jar', txt.get()], stderr=subprocess.DEVNULL)

	processo_return_s = str(processo_return, 'ISO-8859-1')

	if processo_return_s[:4] == "HELP":

		lbl_saida.configure (text="Erro. Consulte a ajuda.")

	else:

		lbl_saida.configure (text=processo_return_s.rstrip("\n"))

def clicked2 ():

	stringajuda = subprocess.check_output(['java', '-jar', 'JavaAntonioVandreEval.jar', 'help'], stderr=subprocess.DEVNULL)

	windowajuda = Tk ()

	windowajuda.resizable (False, False)

	windowajuda.title ("AntonioVandreEvalGUIAjuda")

	S = tk.Scrollbar (windowajuda)

	T = tk.Text (windowajuda, height=30, width=100)

	S.pack (side=tk.RIGHT, fill=tk.Y)

	T.pack (side=tk.LEFT, fill=tk.Y)

	S.config (command=T.yview)

	T.config (yscrollcommand=S.set)

	stringajuda_s = str(stringajuda, 'ISO-8859-1')

	T.insert (tk.INSERT, stringajuda_s[4:])

	windowajuda.mainloop ()

def clicked3 ():

	sys.exit (0)

btn_calcular = Button (window, text="Calcular", command=clicked)

btn_calcular.place (anchor='center', relx=.2, rely=.5)

btn_ajuda = Button (window, text="Ajuda", command=clicked2)

btn_ajuda.place (anchor='center', relx=.5, rely=.5)

btn_exit = Button (window, text="Sair", command=clicked3)

btn_exit.place (anchor='center', relx=.8, rely=.5)

window.mainloop ()
