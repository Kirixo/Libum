import os

def remove_descriptions_and_binaries_manually(xml_file):
    with open(xml_file, 'r', encoding='utf-8') as file:
        lines = file.readlines()

    updated_lines = []
    skip = False
    image_id = None
    skipping_binary = False  # Flag to track if we're skipping a specific binary block

    for line in lines:
        if '<description>' in line:
            skip = True
        elif '</description>' in line:
            skip = False
            continue

        if '<FictionBook ' in line:  # Match the FictionBook opening tag
            continue
        if '</FictionBook>' in line:  # Match the FictionBook closing tag
            continue

        if image_id and f'id="{image_id}"' in line and '<binary ' in line:
            skipping_binary = True
            continue
        elif skipping_binary and '</binary>' in line:
            skipping_binary = False
            continue

        if skip or skipping_binary:
            if '<image ' in line and 'href="' in line:
                start_idx = line.find('href="') + 6
                end_idx = line.find('"', start_idx)
                image_id = line[start_idx:end_idx].lstrip('#')
            continue

        updated_lines.append(line)

    # Modify the file name to have the '.c_fb2' extension
    new_file_name = xml_file.rsplit('.', 1)[0] + '.c_fb2'

    with open(new_file_name, 'w', encoding='utf-8') as file:
        file.writelines(updated_lines)

    print(f"Updated XML file saved: {new_file_name}")


# Path to the folder containing the XML files
folder_path = "C:\\Users\\Kirixo\\Desktop\\bookfiles"

# Loop through all files in the folder
for filename in os.listdir(folder_path):
    if filename.endswith(".fb2"):  # You can change this to '.xml' if needed
        try:
            file_path = os.path.join(folder_path, filename)
            remove_descriptions_and_binaries_manually(file_path)
        except Exception as e:
            print(f"Error: {e}\nFilepath: {filename}")
