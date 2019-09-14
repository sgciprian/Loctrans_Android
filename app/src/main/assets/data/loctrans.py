# LOCTRANS schedule generator ~ CRiSTi, xizz3l

from datetime import time
from datetime import datetime
from datetime import timedelta

# dt[i] - time it takes to reach station i from station i-1
# if first stop (dt[0]), then arrival time
#
# tr[i] - time difference between trip starting times
# if first trip(tr[0]), then arrival time

#Add minutes to time
def add_time(tm, min):
    fulldate = datetime(1000, 1, 1, tm.hour, tm.minute, tm.second)
    fulldate = fulldate + timedelta(minutes=min)
    return fulldate.time()

# Generic writer for all lines
def write_line(dt, tr, stops, start_time, file):
	for stop_nr in range( len(stops) ):
		file.write(stops[stop_nr])
		if stop_nr < len(stops)-1:
			file.write(",")
		else:
			file.write("\n")
		
	for trip_nr in range( len(tr) ):
		start_time = add_time(start_time, tr[trip_nr])
		cr_time = start_time
		for stop_nr in range( len(stops) ):
			cr_time = add_time(cr_time, dt[stop_nr])
			file.write(cr_time.strftime("%H:%M"))
			if stop_nr < len(stops)-1:
				file.write(",")
			else:
				file.write("\n")

def generate_L1_LV_T():
	dt = [0, 3, 3, 1, 1, 2, 1, 1, 1, 2, 2, 1]
	tr = [0, 50, 50, 50, 50, 55, 50, 85, 50, 50, 50, 55, 50, 50, 50, 50, 50]
	stops = ["ACH", "CATEDRALA", "SPITAL", "ACR", "ROMTELECOM", "UNION", "MINULESCU", "ARCULUI", "FINANTE", "VALCEA", "METALURGIC", "STEAUA"]
	
	file = open("l1_lv_t.csv", "w")
	start_time = time(6)
	write_line(dt, tr, stops, start_time, file)
	file.close()
	
def generate_L1_LV_R():
	dt = [0, 1, 2, 2, 2, 2, 3, 3]
	tr = [0, 50, 50, 50, 50, 50, 55, 50, 85, 50, 50, 50, 55, 50, 50, 50, 50]
	stops = ["STEAUA", "METALURGIC", "FABRA", "LPS", "HELIOS", "PARC HOTEL", "CATEDRALA", "ACH"]
	
	file = open("l1_lv_r.csv", "w")
	start_time = time(5, 35)
	write_line(dt, tr, stops, start_time, file)
	file.close()
	
def generate_L1_SD_T():
	dt = [0, 4, 2, 1, 1, 1, 2, 2, 1, 2, 1, 1]
	tr = [0, 80, 80, 80, 80, 50]
	stops = ["ACH", "CATEDRALA", "SPITAL", "ACR", "ROMTELECOM", "UNION", "MINULESCU", "ARCULUI", "FINANTE", "VALCEA", "METALURGIC", "STEAUA"]
	
	file = open("l1_sd_t.csv", "w")
	start_time = time(7)
	write_line(dt, tr, stops, start_time, file)
	file.close()

def generate_L1_SD_R():
	dt = [0, 1, 1, 2, 2, 2, 3, 3]
	tr = [0, 80, 80, 80, 80, 50]
	stops = ["STEAUA", "METALURGIC", "FABRA", "LPS", "HELIOS", "PARC HOTEL", "CATEDRALA", "ACH"]

	file = open("l1_sd_r.csv", "w")
	start_time = time(7, 25)
	write_line(dt, tr, stops, start_time, file)
	file.close()

def generate_L1A_LV_T():
	dt = [0, 3, 3, 1, 1, 2, 1, 2, 3, 2]
	tr = [0, 80, 400]
	stops = ["ACH", "CATEDRALA", "SPITAL", "ACR", "ROMTELECOM", "VALCEA", "METALURGIC", "STEAUA", "PIRELLI", "TMK"]

	file = open("l1a_lv_t.csv", "w")
	start_time = time(6)
	write_line(dt, tr, stops, start_time, file)
	file.close()

def generate_L1A_LV_R():
	dt = [0, 3, 3, 1, 2, 2, 2, 2, 3, 3]
	tr = [0, 530]
	stops = ["TMK", "PIRELLI", "STEAUA", "METALURGIC", "FABRA", "LPS", "HELIOS", "PARC HOTEL", "CATEDRALA", "ACH"]

	file = open("l1a_lv_r.csv", "w")
	start_time = time(6, 30)
	write_line(dt, tr, stops, start_time, file)
	file.close()

def generate_L1A_SD_T():
	dt = [0, 3, 2, 2, 1, 3, 1, 1, 4, 2]
	tr = [0]
	stops = ["ACH", "CATEDRALA", "SPITAL", "ACR", "ROMTELECOM", "VALCEA", "METALURGIC", "STEAUA", "PIRELLI", "TMK"]

	file = open("l1a_sd_t.csv", "w")
	start_time = time(5, 50)
	write_line(dt, tr, stops, start_time, file)
	file.close()

def generate_L1B_LV_T():
	dt = [0, 3, 3, 1, 1, 2, 1, 1, 1, 2, 2, 2, 2, 1, 3, 3]
	tr = [0, 480, 480]
	stops = ["ACH", "CATEDRALA", "SPITAL", "ACR", "ROMTELECOM", "UNION", "MINULESCU", "ARCULUI", "FINANTE", "VALCEA", "METALURGIC", "TUNARI", "ARTILERIEI", "CAO", "PRYSMIAN1", "PRYSMIAN2"]
	
	file = open("l1b_lv_t.csv", "w")
	start_time = time(6)
	write_line(dt, tr, stops, start_time, file)
	file.close()
	
def generate_L1B_LV_R():
	dt = [0, 3, 3, 2, 1, 3, 2, 2, 2, 2, 1, 2, 2, 6, 3]
	tr = [0, 480, 480]
	stops = ["PRYSMIAN2", "PRYSMIAN1", "LIDL", "GARA", "TUNARI", "METALURGIC", "FABRA", "LPS", "HELIOS", "ACR", "ROMTELECOM", "FINANTE", "13 DECEMBRIE", "CATEDRALA", "ACH"]
	
	file = open("l1b_lv_r.csv", "w")
	start_time = time(7, 20)
	write_line(dt, tr, stops, start_time, file)
	file.close()

def generate_L1B_SD_T():
	dt = [0, 3, 3, 1, 1, 2, 2, 1, 1, 2, 1, 2, 1, 2, 3, 5]
	tr = [0, 730, 240]
	stops = ["ACH", "CATEDRALA", "SPITAL", "ACR", "ROMTELECOM", "UNION", "MINULESCU", "ARCULUI", "FINANTE", "VALCEA", "METALURGIC", "TUNARI", "ARTILERIEI", "CAO", "PRYSMIAN1", "PRYSMIAN2"]
	
	file = open("l1b_sd_t.csv", "w")
	start_time = time(5, 50)
	write_line(dt, tr, stops, start_time, file)
	file.close()
	
def generate_L1B_SD_R():
	dt = [0, 5, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 3, 2, 3]
	tr = [0, 720, 240]
	stops = ["PRYSMIAN2", "PRYSMIAN1", "LIDL", "GARA", "TUNARI", "METALURGIC", "FABRA", "LPS", "HELIOS", "ACR", "ROMTELECOM", "FINANTE", "13 DECEMBRIE", "CATEDRALA", "ACH"]
	
	file = open("l1b_sd_r.csv", "w")
	start_time = time(7, 20)
	write_line(dt, tr, stops, start_time, file)
	file.close()

def generate_L1C_LV_T():
	dt = [0, 3, 3, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 1, 1, 7]
	tr = [0, 65, 415, 115, 355]
	stops = ["ACH", "CATEDRALA", "SPITAL", "ACR", "ROMTELECOM", "UNION", "MINULESCU", "ARCULUI", "FINANTE", "VALCEA", "METALURGIC", "TUNARI", "ARTILERIEI", "CAO", "PIRELLI", "TMK"]
	
	file = open("l1c_lv_t.csv", "w")
	start_time = time(5, 55)
	write_line(dt, tr, stops, start_time, file)
	file.close()
	
def generate_L1C_LV_R():
	dt = [0, 2, 3, 3, 4, 2, 2, 2, 2, 1, 1, 3, 2, 2]
	tr = [0, 60, 475, 55, 425]
	stops = ["TMK", "PIRELLI", "LIDL", "GARA", "METALURGIC", "VALCEA", "LPS", "HELIOS", "ACR", "ROMTELECOM", "FINANTE", "13 DECEMBRIE", "CATEDRALA", "ACH"]
	
	file = open("l1c_lv_r.csv", "w")
	start_time = time(6, 25)
	write_line(dt, tr, stops, start_time, file)
	file.close()
	
def generate_L1C_SD_T():
	dt = [0, 3, 3, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 1, 1, 2]
	tr = [0, 480, 470]
	stops = ["ACH", "CATEDRALA", "SPITAL", "ACR", "ROMTELECOM", "UNION", "MINULESCU", "ARCULUI", "FINANTE", "VALCEA", "METALURGIC", "TUNARI", "ARTILERIEI", "CAO", "PIRELLI", "TMK"]
	
	file = open("l1c_sd_t.csv", "w")
	start_time = time(5, 55)
	write_line(dt, tr, stops, start_time, file)
	file.close()
	
def generate_L1C_SD_R():
	dt = [0, 2, 3, 3, 4, 2, 2, 2, 2, 1, 1, 3, 2, 2]
	tr = [0, 535, 480]
	stops = ["TMK", "PIRELLI", "LIDL", "GARA", "METALURGIC", "VALCEA", "LPS", "HELIOS", "ACR", "ROMTELECOM", "FINANTE", "13 DECEMBRIE", "CATEDRALA", "ACH"]
	
	file = open("l1c_sd_r.csv", "w")
	start_time = time(6, 25)
	write_line(dt, tr, stops, start_time, file)
	file.close()

def generate_L2_LV_T():
	dt = [0, 1, 2, 1, 1, 2, 2, 2, 2, 3]
	tr = [0, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 60, 25, 25, 25, 25, 25, 25, 25, 15, 35, 15, 50, 50, 50, 50, 50, 50]
	stops = ["GARA", "ARTILERIEI", "CAO", "STEAUA", "METALURGIC", "VALCEA", "LPS", "HELIOS", "PARC HOTEL", "CATEDRALA"]
	
	file = open("l2_lv_t.csv", "w")
	start_time = time(5, 30)
	write_line(dt, tr, stops, start_time, file)
	file.close()
	
def generate_L2_LV_R():
	dt = [0, 3, 1, 1, 2, 1, 1, 2, 2]
	tr = [0, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 60, 25, 25, 25, 25, 25, 50, 15, 35, 15, 50, 50, 50, 50, 50, 50]
	stops = ["CATEDRALA", "SPITAL", "ACR", "ROMTELECOM", "VALCEA", "METALURGIC", "STEAUA", "KAUFLAND", "GARA"]
	
	file = open("l2_lv_r.csv", "w")
	start_time = time(5, 55)
	write_line(dt, tr, stops, start_time, file)
	file.close()
	
def generate_L2_SD_T():
	dt = [0, 1, 2, 1, 1, 2, 2, 2, 2, 3]
	tr = [0, 100, 50, 50, 50, 85, 50, 50, 50, 90, 50, 50, 50, 50, 50, 50]
	stops = ["GARA", "ARTILERIEI", "CAO", "STEAUA", "METALURGIC", "VALCEA", "LPS", "HELIOS", "PARC HOTEL", "CATEDRALA"]
	
	file = open("l2_sd_t.csv", "w")
	start_time = time(5, 30)
	write_line(dt, tr, stops, start_time, file)
	file.close()

def generate_L2_SD_R():
	dt = [0, 3, 1, 1, 2, 1, 1, 2, 2]
	tr = [0, 100, 50, 50, 50, 85, 50, 50, 50, 90, 50, 50, 50, 50, 50, 50]
	stops = ["CATEDRALA", "SPITAL", "ACR", "ROMTELECOM", "VALCEA", "METALURGIC", "STEAUA", "KAUFLAND", "GARA"]
	
	file = open("l2_sd_r.csv", "w")
	start_time = time(5, 55)
	write_line(dt, tr, stops, start_time, file)
	file.close()

def generate_L3_LV_T():
	dt = [0, 2, 3, 10]
	tr = [0, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 50, 30, 30, 40, 30, 30, 40, 30, 30, 30, 30, 30, 35, 40, 30]
	stops = ["LPS", "OMV", "AXXA", "EROILOR"]
	
	file = open("l3_lv_t.csv", "w")
	start_time = time(5, 50)
	write_line(dt, tr, stops, start_time, file)
	file.close()
	
def generate_L3_LV_R():
	dt = [0, 5, 1, 2, 2, 2, 3]
	tr = [0, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 50, 30, 30, 40, 30, 30, 40, 30, 30, 30, 30, 30, 35, 40, 30]
	stops = ["EROILOR", "DECORA", "TMUCB", "GARA", "TUNARI", "FABRA", "LPS"]
	
	file = open("l3_lv_r.csv", "w")
	start_time = time(6, 5)
	write_line(dt, tr, stops, start_time, file)
	file.close()

def generate_L3_SD_T():
	dt = [0, 2, 4, 9]
	tr = [0, 80, 80, 80]
	stops = ["LPS", "OMV", "AXXA", "EROILOR"]
	
	file = open("l3_sd_t.csv", "w")
	start_time = time(7, 35)
	write_line(dt, tr, stops, start_time, file)
	file.close()
	
def generate_L3_SD_R():
	dt = [0, 6, 2, 2, 2, 2, 1]
	tr = [0, 80, 80, 80]
	stops = ["EROILOR", "DECORA", "TMUCB", "GARA", "TUNARI", "FABRA", "LPS"]
	
	file = open("l3_sd_r.csv", "w")
	start_time = time(7, 50)
	write_line(dt, tr, stops, start_time, file)
	file.close()

def generate_L4_LV_T():
	dt = [0, 3, 2, 1, 2, 2, 2, 4, 2, 1, 6]
	tr = [0, 45, 50, 50, 50, 50, 50, 75, 60, 50, 25, 100, 65, 90, 60, 50, 95]
	stops = ["CATEDRALA", "SPITAL", "ACR", "ROMTELECOM", "FINANTE", "TRIBUNAL", "LPS", "OMV", "ALTUR", "ALRO", "SATU NOU"]
	
	file = open("l4_lv_t.csv", "w")
	start_time = time(5, 50)
	write_line(dt, tr, stops, start_time, file)
	file.close()
	
def generate_L4_LV_R():
	dt = [0, 6, 2, 1, 2, 2, 2, 1, 1, 2, 2]
	tr = [0, 45, 50, 50, 50, 50, 50, 75, 60, 50, 65, 65, 60, 90, 60, 50, 95]
	stops = ["SATU NOU", "ALRO", "ALTUR", "DECORA", "BAZIN", "HELIOS", "ACR", "ROMTELECOM", "UNION", "13 DECEMBRIE", "CATEDRALA"]
	
	file = open("l4_lv_r.csv", "w")
	start_time = time(6, 15)
	write_line(dt, tr, stops, start_time, file)
	file.close()
	
def generate_L4_SD_T():
	dt = [0, 3, 2, 1, 2, 2, 2, 2, 4, 6]
	tr = [0, 295, 185, 45]
	stops = ["CATEDRALA", "SPITAL", "ACR", "ROMTELECOM", "FINANTE", "TRIBUNAL", "LPS", "OMV", "ALRO", "SATU NOU"]
	
	file = open("l4_sd_t.csv", "w")
	start_time = time(8, 5)
	write_line(dt, tr, stops, start_time, file)
	file.close()
	
def generate_L4_SD_R():
	dt = [0, 6, 2, 1, 2, 2, 2, 1, 1, 2, 2]
	tr = [0, 295, 180, 50]
	stops = ["SATU NOU", "ALRO", "ALTUR", "DECORA", "BAZIN", "HELIOS", "ACR", "ROMTELECOM", "UNION", "13 DECEMBRIE", "CATEDRALA"]
	
	file = open("l4_sd_r.csv", "w")
	start_time = time(8, 30)
	write_line(dt, tr, stops, start_time, file)
	file.close()
	
def generate_L5_LV_T():
	dt = [0, 4, 2, 4, 3, 2, 2]
	tr = [0, 55, 55, 55, 85, 55, 55, 55, 55, 55, 55, 55, 55, 55, 85, 65]
	stops = ["CLOCOCIOV", "BANULUI", "CUZA VODA", "LIDL", "GARA", "TUNARI", "VALCEA"]
	
	file = open("l5_lv_t.csv", "w")
	start_time = time(6)
	write_line(dt, tr, stops, start_time, file)
	file.close()
	
def generate_L5_LV_R():
	dt = [0, 3, 3, 3, 2, 3, 4, 10]
	tr = [0, 55, 55, 55, 85, 55, 54, 56, 55, 55, 55, 55, 55, 55, 85, 65]
	stops = ["VALCEA", "LPS", "HELIOS", "ACR", "ROMTELECOM", "FINANTE", "MINULESCU", "CLOCOCIOV"]
	
	file = open("l5_lv_r.csv", "w")
	start_time = time(6, 17)
	write_line(dt, tr, stops, start_time, file)
	file.close()
	
def generate_L5_SD_T():
	dt = [0, 4, 2, 4, 3, 2, 2]
	tr = [0, 55, 55, 200, 55, 175]
	stops = ["CLOCOCIOV", "BANULUI", "CUZA VODA", "LIDL", "GARA", "TUNARI", "VALCEA"]
	
	file = open("l5_sd_t.csv", "w")
	start_time = time(7, 40)
	write_line(dt, tr, stops, start_time, file)
	file.close()
	
def generate_L5_SD_R():
	dt = [0, 3, 3, 3, 2, 3, 4, 10]
	tr = [0, 55, 55, 200, 55, 175]
	stops = ["VALCEA", "LPS", "HELIOS", "ACR", "ROMTELECOM", "FINANTE", "MINULESCU", "CLOCOCIOV"]
	
	file = open("l5_sd_r.csv", "w")
	start_time = time(7, 57)
	write_line(dt, tr, stops, start_time, file)
	file.close()
	
def generate_L5B_LV_T():
	dt = [0, 6, 1, 1, 1, 1, 1, 2, 2]
	tr = [0, 40, 30, 410, 40]
	stops = ["CLOCOCIOV", "STEAUA", "METALURGIC", "TUNARI", "AUTOGARA", "TMUCB", "DEDEMAN", "ALTUR", "ALRO"]
	
	file = open("l5b_lv_t.csv", "w")
	start_time = time(6)
	write_line(dt, tr, stops, start_time, file)
	file.close()
	
def generate_L5B_LV_R():
	dt = [0, 6, 1, 1, 1, 1, 1, 2, 2]
	tr = [0, 40, 35, 405, 75]
	stops = ["ALRO", "ALTUR", "DEDEMAN", "TMUCB", "GARA", "TUNARI", "METALURGIC", "STEAUA", "CLOCOCIOV"]
	
	file = open("l5b_lv_r.csv", "w")
	start_time = time(6, 15)
	write_line(dt, tr, stops, start_time, file)
	file.close()
	
def generate_L5B_SD_T():
	file = open("l5b_sd_t.csv", "w")
	file.close()
	
def generate_L5B_SD_R():
	file = open("l5b_sd_r.csv", "w")
	file.close()

#    TEMPLATE
#def generate_Lx_WW_Y():	
#	dt = []
#	tr = []
#	stops = []
#	
#	file = open("lx_ww_y.csv", "w")
#	start_time = time(t)
#	write_line(dt, tr, stops, start_time, file)
#	file.close()

def main():
	generate_L1_LV_T()
	generate_L1_LV_R()
	generate_L1_SD_T()
	generate_L1_SD_R()

	generate_L1A_LV_T()
	generate_L1A_LV_R()
	generate_L1A_SD_T()
	
	generate_L1B_LV_T()
	generate_L1B_LV_R()
	generate_L1B_SD_T()
	generate_L1B_SD_R()
	
	generate_L1C_LV_T()
	generate_L1C_LV_R()
	generate_L1C_SD_T()
	generate_L1C_SD_R()
	
	generate_L2_LV_T()
	generate_L2_LV_R()
	generate_L2_SD_T()
	generate_L2_SD_R()
	
	generate_L3_LV_T()
	generate_L3_LV_R()
	generate_L3_SD_T()
	generate_L3_SD_R()
	
	generate_L4_LV_T()
	generate_L4_LV_R()
	generate_L4_SD_T()
	generate_L4_SD_R()
	
	generate_L5_LV_T()
	generate_L5_LV_R()
	generate_L5_SD_T()
	generate_L5_SD_R()
	
	generate_L5B_LV_T()
	generate_L5B_LV_R()
	generate_L5B_SD_T()
	generate_L5B_SD_R()
	
if __name__ == "__main__":
    main()
