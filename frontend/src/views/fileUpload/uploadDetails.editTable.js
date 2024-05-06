import EditUserDialog from "../../components/EditUserDialog/editUserDialog";
import React, {useEffect, useState} from "react";
import api from "../../services/apiAxios";
import BaseTable from "../../components/BaseTable/baseTable";
import detailedUserTableColumns from "../../utils/translations/detailedUserTableColumns";
import {useSnackbar} from "../../components/Snackbar/snackbar";

const UploadDetailsEditTable = ({ data }) => {
    const [editDialogOpen, setEditDialogOpen] = useState(false);
    const [selectedUser, setSelectedUser] = useState(null);
    const [editedData, setEditedData] = useState([]);
    const [loading, setLoading] = useState(false);
    const {handleRequestError} = useSnackbar();

    useEffect(() => {
        setEditedData(data);
    }, [data]);

    const handleEditRow = (user) => {
        setSelectedUser(user);
        setEditDialogOpen(true);
    };

    const handleEditDialogClose = () => {
        setEditDialogOpen(false);
        setSelectedUser(null);
    };

    const handleEditSubmit = async (newName, newSurname, newLogin) => {
        if (selectedUser) {
            try {
                setLoading(true)
                const updatedUserData = {
                    id: selectedUser.id,
                    name: newName || selectedUser.name,
                    surname: newSurname || selectedUser.surname,
                    login: newLogin || selectedUser.login
                };
                await api.editUser({
                    endpoint: 'user',
                    userData: updatedUserData
                });
                const updatedData = editedData.map(item => {
                    if (item.id === selectedUser.id) {
                        return {
                            ...item,
                            ...updatedUserData
                        };
                    } else {
                        return item;
                    }
                });

                setEditedData(updatedData);
            } catch (error) {
                handleRequestError(error);
            }
            finally {
                setLoading(false)
            }
        }
        handleEditDialogClose();
    };

    const handleDeleteRow = async (userId) => {
        const confirmation = window.confirm('Czy na pewno chcesz usunąć ten obiekt?');
        if (confirmation) {
            try {
                await api.dellUser({
                    endpoint: 'user',
                    id: userId
                });
                setEditedData(editedData.filter(item => item.id !== userId));
            } catch (error) {
                handleRequestError(error);
            }
        }
    };

    return(
        <>
            <div>
                <h4>Lista niepełnych obiektów</h4>
                <BaseTable
                    data={editedData}
                    columns={detailedUserTableColumns}
                    handleEditRow={handleEditRow}
                    handleDeleteRow={handleDeleteRow}
                    loading={loading}
                />
            </div>
            <EditUserDialog
                open={editDialogOpen}
                onClose={handleEditDialogClose}
                user={selectedUser}
                onSubmit={handleEditSubmit}
            />
        </>
    )
}

export default UploadDetailsEditTable;