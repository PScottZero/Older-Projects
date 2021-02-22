# STP Ground Station GUI
# Student Space Programs Laboratory
#
# 24 September 2017
from tkinter import *
from datetime import datetime
import serial
import os

# color themes for GUI

# grey theme
# color_bg = "#6B6B6B"
# color_fg = "#9A9A9A"
# color_font = "#00007F"

# blue theme
color_bg = "#367189"
color_fg = "#729bac"
color_font = "#00007F"


# builds GUI elements
class STPGround:

    # initializer class
    def __init__(self, master):

        self.row = 0
        self.col = 0
        self.pad = 0
        self.count = 0
        self.run = True

        # file writing data
        self.desktop = os.path.join(os.path.join(os.environ['HOMEPATH']), 'Desktop')
        self.file = open(os.path.join(self.desktop, 'data.txt'), 'a')
        self.error_file = open(os.path.join(self.desktop, 'error_data.txt'), 'a')

        # initializes upper and lower frames
        self.upperFrame = Frame(master, bg=color_bg)
        self.upperFrame.pack()
        self.middleFrame = Frame(master, bg=color_bg)
        self.middleFrame.pack(anchor="w", padx=20, pady=(30, 0))

        # initializes logo for upper frame
        self.logo = PhotoImage(file="logo.png")
        self.logoLabel = Label(self.upperFrame, image=self.logo, borderwidth=0, highlightthickness=0, bg=color_bg)
        self.logoLabel.image = self.logo
        self.logoLabel.pack()

        # timestamp cell
        self.timestamp = StringVar()
        self.widget_creator(self.timestamp, "Time Stamp:")

        # altitude cell
        self.altitude = StringVar()
        self.widget_creator(self.altitude, "Altitude:")

        # latitude cell
        self.latitude = StringVar()
        self.widget_creator(self.latitude, "Latitude:")

        # longitude cell
        self.longitude = StringVar()
        self.widget_creator(self.longitude, "Longitude:")

        # temperature cell
        self.temperature = StringVar()
        self.widget_creator(self.temperature, "Temperature:")

        # acceleration x cell
        self.accX = StringVar()
        self.widget_creator(self.accX, "Acceleration X:")

        # acceleration y cell
        self.accY = StringVar()
        self.widget_creator(self.accY, "Acceleration Y:")

        # acceleration z cell
        self.accZ = StringVar()
        self.widget_creator(self.accZ, "Acceleration Z:")

        # acceleration z cell
        self.voltage = StringVar()
        self.widget_creator(self.voltage, "Voltage Draw:")

        # acceleration z cell
        self.uv = StringVar()
        self.widget_creator(self.uv, "UV Index:")

        # acceleration z cell
        self.ejection = StringVar()
        self.widget_creator(self.ejection, "Ejection Charge:")

        # gas 1 concentration cell
        self.gas1 = StringVar()
        self.widget_creator(self.gas1, "Alcohol Concentration:")

        # gas 2 concentration cell
        self.gas2 = StringVar()
        self.widget_creator(self.gas2, "Humidity:")

        # connection status cell
        self.connection = StringVar()
        self.widget_creator(self.connection, "Connection Status:")

        # sets dummy data
        self.dummy_data()

    # creates data cells
    def widget_creator(self, variable, text):
        widget_frame = Frame(self.middleFrame, bg=color_fg, width=220, height=65)
        widget_frame.grid_propagate(0)
        widget_frame.grid_columnconfigure(0, weight=1)
        widget_frame.grid(padx=(self.pad, 0), pady=(0, 20), row=self.row, column=self.col)
        widget_label = Label(widget_frame, bg=color_fg, text=text, fg=color_font, font=("Arial", 12, "italic"))
        widget_label.config(pady=2)
        widget_label.grid()
        widget_value = Label(widget_frame, bg=color_fg, textvariable=variable, font=("Arial", 16))
        widget_value.grid()

        if self.count % 2 == 0:
            self.pad = 20
            self.col = 1
        else:
            self.pad = 0
            self.row += 1
            self.col = 0
        self.count += 1

    # decodes data string
    def set_data(self, data_str):
        split = data_str.split(",")
        if len(split) != 13:
            self.timestamp.set("Error")
            self.latitude.set("Error")
            self.longitude.set("Error")
            self.altitude.set("Error")
            self.temperature.set("Error")
            self.accX.set("Error")
            self.accY.set("Error")
            self.accZ.set("Error")
            self.voltage.set("Error")
            self.uv.set("Error")
            self.ejection.set("Error")
            self.gas1.set("Error")
            self.gas2.set("Error")
            self.error_file.write(data_str)
        else:
            if split[0].find("\n") != -1:
                split[0] = split[0][2:]
            self.timestamp.set(split[0])
            self.latitude.set(split[1])
            self.longitude.set(split[2])
            self.altitude.set(split[3] + "m")
            self.temperature.set(split[4] + "\u00b0C")
            self.accX.set(split[5] + "m/2\u00b2")
            self.accY.set(split[6] + "m/2\u00b2")
            self.accZ.set(split[7] + "m/2\u00b2")
            self.voltage.set(split[8] + "V")
            self.uv.set(split[9])
            self.ejection.set(split[10])
            self.gas1.set(split[11])
            self.gas2.set(split[12] + "%")
            self.file.write(data_str)

    # properly ends python window
    def on_close(self):
        self.file.close()
        self.error_file.close()
        self.run = False
        root.destroy()

    # dummy data for GUI
    def dummy_data(self):
        self.timestamp.set(datetime.now().strftime("%H:%M:%S"))
        self.altitude.set("56.060m")
        self.latitude.set("40.79639")
        self.longitude.set("-77.86790")
        self.temperature.set("3.232\u00b0C")
        self.accX.set("3.211m/s\u00b2")
        self.accY.set("5.409m/s\u00b2")
        self.accZ.set("10.652m/s\u00b2")
        self.voltage.set("4.955V")
        self.uv.set("3.2")
        self.ejection.set("False")
        self.gas1.set("407.060")
        self.gas2.set("22%")
        self.connection.set("Dummy Data")

    # sets all data to null (None)
    def null_data(self):
        self.timestamp.set(datetime.now().strftime("%H:%M:%S"))
        self.altitude.set(None)
        self.latitude.set(None)
        self.longitude.set(None)
        self.temperature.set(None)
        self.accX.set(None)
        self.accY.set(None)
        self.accZ.set(None)
        self.voltage.set(None)
        self.uv.set(None)
        self.ejection.set(None)
        self.gas1.set(None)
        self.gas2.set(None)


# configures the root window of the application
root = Tk()
root.title("SSPL STP Ground Station")
root.configure(bg=color_bg)
root.resizable(width=False, height=False)

# image icon for application
img = PhotoImage(file="icon.png")
root.call("wm", "iconphoto", root, img)

# assembles and runs GUI
s = STPGround(root)
root.protocol('WM_DELETE_WINDOW', s.on_close)

# PySerial Initialization
try:
    root.update()
    ser = serial.Serial("COM3")  # set serial port here
    ser.baudrate = 9600
    data = ""
    data_strings = []
    s.connection.set("Connected")
    s.null_data()
    while s.run:
        root.update_idletasks()
        root.update()
        x = ser.read().decode()
        if x == ';':
            s.set_data(data)
            data = ""
        else:
            data += x

except serial.SerialException:
    s.connection.set("No Connection")
    s.null_data()
    # s.dummy_data()
    root.mainloop()
