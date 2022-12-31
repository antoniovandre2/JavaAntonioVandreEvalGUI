# Proprietário: Antonio Vandré Pedrosa Furtunato Gomes (bit.ly/antoniovandre_legadoontologico).

# Projeto Mathematical Ramblings (mathematicalramblings.blogspot.com).

# Arquivo Python para "AntonioVandreEval", versão de distribuição.

# Última atualização: 31-12-2022.

import tkinter as tk

from tkinter import *

import os

import sys

import subprocess

import ctypes

# DIRETÓRIO DE INSTALAÇÃO CUSTOMIZÁVEL.

os.chdir ("C:\JavaAntonioVandreEvalGUI")

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

bglabel.place (x=0, y=0)

window.title ("JavaAntonioVandreEvalGUIW 31-12-2022")

lbl_entre = Label (window, text="Expressão:")

lbl_entre.place (x=5, y=5)

txt = Entry (window, width=66)

txt.place (x=5, y=35)

lbl_resultado = Label (window, text="Resultado:")

lbl_resultado.place (x=5, y=98)

lbl_saida = Label (window, text="Entre com a expressão e clique em calcular.")

lbl_saida.place (x=5, y=123)

processo_return = subprocess.check_output(['java', '-jar', 'JavaAntonioVandreEval.jar', "version"], stderr=subprocess.DEVNULL)

processo_return_s = str(processo_return, 'ISO-8859-1')

lbl_backend = Label (window, text="Backend: " + processo_return_s.rstrip("\n"))

lbl_backend.place (x=270, y=5)

def clicked ():

	processo_return = subprocess.check_output(['java', '-jar', 'JavaAntonioVandreEval.jar', "version"], stderr=subprocess.DEVNULL)

	processo_return_s = str(processo_return, 'ISO-8859-1')

	lbl_backend.configure (text="Backend: " + processo_return_s.rstrip("\n"))

	processo_return = subprocess.check_output(['java', '-jar', 'JavaAntonioVandreEval.jar', txt.get()], stderr=subprocess.DEVNULL)

	processo_return_s = str(processo_return, 'ISO-8859-1')

	if processo_return_s[:4] == "HELP":

		lbl_saida.configure (text="Erro. Consulte a ajuda.")

	else:

		lbl_saida.configure (text=processo_return_s.rstrip("\n"))

def clicked2 ():

	processo_return = subprocess.check_output(['java', '-jar', 'JavaAntonioVandreEval.jar', "version"], stderr=subprocess.DEVNULL)

	processo_return_s = str(processo_return, 'ISO-8859-1')

	lbl_backend.configure (text="Backend: " + processo_return_s.rstrip("\n"))

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
